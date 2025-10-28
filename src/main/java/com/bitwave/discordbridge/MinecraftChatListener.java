package com.bitwave.discordbridge;

import com.bitwave.discordbridge.DiscordBridge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MinecraftChatListener implements Listener {
    private final DiscordBridge plugin;

    public MinecraftChatListener(DiscordBridge plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = "**" + event.getPlayer().getName() + "**: " + event.getMessage();

        if (plugin.getBotManager() != null) {
            plugin.getBotManager().sendMessage(message);
        }
    }
}
