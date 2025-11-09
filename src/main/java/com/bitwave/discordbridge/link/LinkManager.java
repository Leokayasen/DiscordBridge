package com.bitwave.discordbridge.link;

import com.bitwave.discordbridge.DiscordBridge;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class LinkManager {
    private final DiscordBridge plugin;
    private final File linkingFile;
    private final File linkedFile;
    private final FileConfiguration linkingConfig;
    private final FileConfiguration linkedConfig;

    private final Map<String, UUID> pendingLinks = new HashMap<>();

    public LinkManager(DiscordBridge plugin) {
        this.plugin = plugin;

        linkingFile = new File(plugin.getDataFolder(), "linking.yml");
        linkedFile = new File(plugin.getDataFolder(), "linked.yml");

        if (!linkingFile.exists()) plugin.saveResource("linking.yml", false);
        if (!linkedFile.exists()) plugin.saveResource("linked.yml", false);

        linkingConfig = YamlConfiguration.loadConfiguration(linkingFile);
        linkedConfig = YamlConfiguration.loadConfiguration(linkedFile);

        loadPendingLinks();
    }

    private void loadPendingLinks() {
        if (linkingConfig.getConfigurationSection("codes") != null) {
            for (String code : linkingConfig.getConfigurationSection("codes").getKeys(false)) {
                UUID uuid = UUID.fromString(linkingConfig.getString("codes." + code));
                pendingLinks.put(code, uuid);
            }
        }
    }

    private void savePendingLinks() {
        linkingConfig.set("codes", null);
        for (Map.Entry<String, UUID> entry : pendingLinks.entrySet()) {
            linkingConfig.set("codes." + entry.getKey(), entry.getValue().toString());
        }
        try {
            linkingConfig.save(linkingFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLinked() {
        try {
            linkedConfig.save(linkedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createLinkCode(UUID playerUUID) {
        String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        pendingLinks.put(code, playerUUID);
        savePendingLinks();
        return code;
    }

    public boolean completeLink(String code, String discordId) {
        if (!pendingLinks.containsKey(code)) return false;

        UUID uuid = pendingLinks.remove(code);
        savePendingLinks();

        linkedConfig.set("links." + uuid.toString(), discordId);
        saveLinked();
        return true;
    }

    public String getDiscordId(UUID uuid) {
        return linkedConfig.getString("links." + uuid.toString());
    }

    public UUID getMinecraftUUID(String discordId) {
        for (String key : linkedConfig.getConfigurationSection("links").getKeys(false)) {
            if (linkedConfig.getString("links." + key).equals(discordId))
                return UUID.fromString(key);
        }
        return null;
    }
}
