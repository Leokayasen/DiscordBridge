package com.bitwave.discordbridge;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class DiscordMessageListener extends ListenerAdapter {
    private final DiscordBridge plugin;
    private final String channelId;

    public DiscordMessageListener(DiscordBridge plugin) {
        this.plugin = plugin;
        this.channelId = plugin.getConfig().getString("discord.channel-id");
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String content = event.getMessage().getContentDisplay();

        // Priv message handling
        if (event.isFromType(ChannelType.PRIVATE)) {
            String code = content.trim();
            boolean ok = plugin.getAccountLink().confirmLink(event.getAuthor().getId(), code);
            if (ok) {
                event.getChannel().sendMessage("✅ Link successful. Your Discord account is now linked.");
                String minecraftUUID = plugin.getAccountLink().getLinkedMinecraft(event.getAuthor().getId());
                if (minecraftUUID != null) {
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        String name = Bukkit.getOfflinePlayer(UUID.fromString(minecraftUUID)).getName();
                        Bukkit.broadcastMessage("[Discord] " + event.getAuthor().getName() + " linked with " + (name != null ? name : minecraftUUID));
                    });
                }
            } else {
                event.getChannel().sendMessage("🔴 Invalid or expired code. Please request a new code from the server using `/discord`");
            }
            return;
        }

        // Channel message to server
        if (!event.getChannel().getId().equals(channelId)) return;
        String author = event.getAuthor().getName();

        // Sanitize content to prevent issues with Minecraft chat
        if (content.isEmpty() || content.length() > 256) return;
        Bukkit.getScheduler().runTask(plugin, () -> Bukkit.broadcastMessage("[Discord] " + author + ": " + content));
    }
}
