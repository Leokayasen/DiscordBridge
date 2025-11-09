package com.bitwave.discordbridge;

import com.bitwave.discordbridge.DiscordBridge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerActivityListener implements Listener {
    private final DiscordBridge plugin;

    public PlayerActivityListener(DiscordBridge plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (plugin.getBotManager() != null) {
            plugin.getBotManager().sendMessage("🟢 **" + event.getPlayer().getName() + "** has joined the server");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (plugin.getBotManager() != null) {
            plugin.getBotManager().sendMessage("🔴 **" + event.getPlayer().getName() + "** has left the server");
        }
    }
}
