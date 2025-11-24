package com.bitwave.discordbridge;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AccountLink {
    private final JavaPlugin plugin;
    private final File file;
    private final FileConfiguration config;

    // pending code -> minecraftUUID
    private final Map<String, String> pending = new ConcurrentHashMap<>();

    public AccountLink(JavaPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "links.yml");

        // Ensure data folder exists
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        // Try use bundled resource; if missing, create empty file
        if (!file.exists()) {
            try {
                // Attempt to extract resource file
                plugin.saveResource("links.yml", false);
            } catch (IllegalArgumentException err) {
                try {
                    if (file.createNewFile()) {
                        FileConfiguration initial = YamlConfiguration.loadConfiguration(file);
                        initial.set("links.discord-to-minecraft", null);
                        initial.set("links.minecraft-to-discord", null);
                        initial.save(file);
                    }
                } catch (IOException ioerr) {
                    plugin.getLogger().warning("⚠️ Failed to create links.yml: " + ioerr.getMessage());
                }
            }
        }

        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public synchronized String generateCode(Player player) {
        String code = Long.toHexString((System.currentTimeMillis() ^ player.getUniqueId().getMostSignificantBits()) & 0xfffffff).toUpperCase();
        code = code.substring(0, Math.min(6, code.length()));
        pending.put(code, player.getUniqueId().toString());

        // Expire after 10 minutes
        String finalCode = code;
        Bukkit.getScheduler().runTaskLater(plugin, () -> pending.remove(finalCode), 20L * 60 * 10);
        return code;
    }

    public synchronized boolean confirmLink(String discordId, String code) {
        String uuid = pending.remove(code);
        if (uuid == null) return false;
        config.set("links.discord-to-minecraft." + discordId, uuid);
        config.set("links.minecraft-to-discord." + uuid, discordId);
        save();
        return true;
    }

    public synchronized String getLinkedMinecraft(String discordId) {
        return config.getString("links.discord-to-minecraft." + discordId);
    }

    public synchronized String getLinkedDiscord(String minecraftUUID) {
        return config.getString("links.minecraft-to-discord." + minecraftUUID);
    }

    private void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("⚠️ Failed to save links.yml: " + e.getMessage());
        }
    }
}
