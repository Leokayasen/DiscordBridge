package com.bitwave.discordbridge;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class DiscordMessageListener extends ListenerAdapter {
    private final DiscordBridge plugin;
    private final String channelId;

    public DiscordMessageListener(DiscordBridge plugin) {
        this.plugin = plugin;
        // Cache channel ID on initialization instead of reading config for every message
        this.channelId = plugin.getConfig().getString("discord.channel-id");
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        // Use cached channel ID instead of reading config
        if (!event.getChannel().getId().equals(channelId)) return;

        String author = event.getAuthor().getName();
        String content = event.getMessage().getContentDisplay();
        
        // Sanitize content to prevent issues with Minecraft chat
        if (content.isEmpty() || content.length() > 256) return;

        // Broadcast to online players
        Bukkit.getScheduler().runTask(plugin, () ->
                Bukkit.broadcastMessage("[Discord] " + author + ": " + content)
                );
    }
}
