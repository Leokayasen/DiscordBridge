package com.bitwave.discordbridge;

import com.bitwave.discordbridge.link.LinkManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class DiscordMessageListener extends ListenerAdapter {
    private final DiscordBridge plugin;
    private final LinkManager linkManager;

    public DiscordMessageListener(DiscordBridge plugin, LinkManager linkManager) {
        this.plugin = plugin;
        this.linkManager = linkManager;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String content = event.getMessage().getContentDisplay().trim();

        // Link command in Discord
        if (content.toLowerCase().startsWith("!link ")) {
            String code = content.substring(6).trim();
            boolean success = linkManager.completeLink(code, event.getAuthor().getId());
            if (success) {
                event.getChannel().sendMessage("✅ Account linked successfully for " + event.getAuthor().getAsMention() + "!").queue();
            } else {
                event.getChannel().sendMessage("❌ Invalid or expired link code.").queue();
            }
            return;
        }

        // Normal Discord-to-Minecraft message handling
        String channelId = plugin.getConfig().getString("discord.channel-id");
        if (!event.getChannel().getId().equals(channelId)) return;

        String author = event.getAuthor().getName();
        Bukkit.getScheduler().runTask(plugin, () ->
                Bukkit.broadcastMessage("[Discord] <" + author + "> " + content)
                );
    }
}
