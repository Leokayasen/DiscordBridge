package com.bitwave.discordbridge;

import com.bitwave.discordbridge.DiscordBridge;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordBotManager {
    private volatile JDA jda;
    private final DiscordBridge plugin;
    private String channelId;
    private TextChannel cachedChannel;

    public DiscordBotManager(DiscordBridge plugin) {
        this.plugin = plugin;
    }

    public void startBot(String token, String channelId) {
        this.channelId = channelId;

        // Start bot connection asynchronously to prevent blocking server startup
        new Thread(() -> {
            try {
                plugin.getLogger().info("🔄 Connecting to Discord...");
                jda = JDABuilder.createDefault(token)
                        .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                        .addEventListeners(new DiscordMessageListener(plugin))
                        .build();
                jda.awaitReady();
                plugin.getLogger().info("✅ Connected to Discord as " + jda.getSelfUser().getName());
            } catch (InvalidTokenException e) {
                plugin.getLogger().severe("❌ Invalid Discord Token");
                jda = null;
            } catch (Exception e) {
                plugin.getLogger().severe("❌ Failed to start Bot: " + e.getMessage());
                e.printStackTrace();
                jda = null;
            }
        }, "DiscordBridge-Connection").start();
    }

    public void sendMessage(String message) {
        if (jda == null || jda.getStatus() != JDA.Status.CONNECTED) {
            return; // Silently skip if not connected yet
        }
        
        // Cache channel lookup to avoid repeated API calls
        if (cachedChannel == null || !cachedChannel.getId().equals(channelId)) {
            cachedChannel = jda.getTextChannelById(channelId);
            if (cachedChannel == null) {
                plugin.getLogger().warning("⚠️ Could not find Discord channel with ID: " + channelId);
                return;
            }
        }
        
        // Queue message with failure callback
        cachedChannel.sendMessage(message).queue(
            null, // Success callback (not needed)
            throwable -> {
                plugin.getLogger().warning("⚠️ Failed to send Discord message: " + throwable.getMessage());
                // Invalidate cache on error
                cachedChannel = null;
            }
        );
    }

    public void shutdown() {
        if (jda != null) {
            try {
                // Clear cache
                cachedChannel = null;
                
                // Graceful shutdown with timeout
                jda.shutdown();
                
                // Wait up to 5 seconds for shutdown
                if (!jda.awaitShutdown(5, java.util.concurrent.TimeUnit.SECONDS)) {
                    plugin.getLogger().warning("⚠️ Discord bot shutdown timed out, forcing shutdown");
                    jda.shutdownNow();
                }
                
                plugin.getLogger().info("🛑 Discord Bot has been shut down.");
            } catch (InterruptedException e) {
                plugin.getLogger().warning("⚠️ Shutdown interrupted: " + e.getMessage());
                jda.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * Check if the Discord bot is connected and ready to send/receive messages
     * @return true if bot is ready
     */
    public boolean isReady() {
        return jda != null && jda.getStatus() == JDA.Status.CONNECTED;
    }
}
