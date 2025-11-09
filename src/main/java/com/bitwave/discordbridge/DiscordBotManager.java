package com.bitwave.discordbridge;

import com.bitwave.discordbridge.DiscordBridge;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordBotManager {
    private JDA jda;
    private final DiscordBridge plugin;
    private String channelId;

    public DiscordBotManager(DiscordBridge plugin) {
        this.plugin = plugin;
    }

    public void startBot(String token, String channelId) {
        this.channelId = channelId;

        try {
            jda = JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                    .addEventListeners(new DiscordMessageListener(plugin))
                    .build();
            jda.awaitReady();
            plugin.getLogger().info("✅ Connect to Discord as " + jda.getSelfUser().getName());
        } catch (InvalidTokenException e) {
            plugin.getLogger().severe("❌ Invalid Discord Token");
        } catch (Exception e) {
            plugin.getLogger().severe("❌ Failed to start Bot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (jda == null) return;
        TextChannel channel = jda.getTextChannelById(channelId);
        if (channel != null)
            channel.sendMessage(message).queue();
    }

    public void shutdown() {
        if (jda != null) {
            jda.shutdown();
            plugin.getLogger().info("🛑 Discord Bot has been shut down.");
        }
    }
}
