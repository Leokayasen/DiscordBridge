package com.bitwave.discordbridge;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class DiscordMessageListener extends ListenerAdapter {
    private final DiscordBridge plugin;

    public DiscordMessageListener(DiscordBridge plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String channelId = plugin.getConfig().getString("discord.channel-id");
        if (!event.getChannel().getId().equals(channelId)) return;

        String author = event.getAuthor().getName();
        String content = event.getMessage().getContentDisplay();

        // Broadcast to online players
        Bukkit.getScheduler().runTask(plugin, () ->
                Bukkit.broadcastMessage("[Discord] " + author + ": " + content)
                );
    }
}
