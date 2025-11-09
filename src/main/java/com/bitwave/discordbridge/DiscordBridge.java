package com.bitwave.discordbridge;

import com.bitwave.discordbridge.DiscordBotManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DiscordBridge extends JavaPlugin {
    private static DiscordBridge instance;
    private DiscordBotManager botManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        String token = getConfig().getString("discord.token");
        String channelId = getConfig().getString("discord.channel-id");

        if (token == null || token.equals("BOT_TOKEN_HERE")) {
            getLogger().warning("⚠️ No valid Discord bot token set in config.yml");
            return;
        }

        botManager = new DiscordBotManager(this);
        botManager.startBot(token, channelId);

        getServer().getPluginManager().registerEvents(new MinecraftChatListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerActivityListener(this), this);

        getLogger().info("DiscordBridge has been enabled!");
    }

    @Override
    public void onDisable() {
        if (botManager != null)
            botManager.shutdown();

        getLogger().info("DiscordBridge has been disabled!");

    }

    public static DiscordBridge getInstance() {
        return instance;
    }

    public DiscordBotManager getBotManager() {
        return botManager;
    }
    
    /**
     * Check if the Discord bot is connected and ready
     * @return true if bot is ready to send/receive messages
     */
    public boolean isBotReady() {
        return botManager != null && botManager.isReady();
    }
}
