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
        String playerName = event.getPlayer().getName();
        String messageContent = event.getMessage();
        
        // Truncate long messages to prevent Discord API issues
        if (messageContent.length() > 1900) {
            messageContent = messageContent.substring(0, 1900) + "...";
        }
        
        String message = "**" + playerName + "**: " + messageContent;

        if (plugin.getBotManager() != null) {
            plugin.getBotManager().sendMessage(message);
        }
    }
}
