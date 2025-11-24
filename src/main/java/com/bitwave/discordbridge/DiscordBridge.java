package com.bitwave.discordbridge;

import org.bukkit.plugin.java.JavaPlugin;

public class DiscordBridge extends JavaPlugin {
    private static DiscordBridge instance;
    private DiscordBotManager botManager;
    private AccountLink accountLink;
    private WebhookManager webhookManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        String token = getConfig().getString("discord.token");
        String channelId = getConfig().getString("discord.channel-id");
        String webhookUrl = getConfig().getString("discord.webhook-url", "").trim();

        this.accountLink = new AccountLink(this);
        this.webhookManager = new WebhookManager(this, webhookUrl);

        if (token == null || token.trim().isEmpty() || token.equals("BOT_TOKEN_HERE")) {
            getLogger().warning("⚠️ No valid Discord bot token set in config.yml");
            return;
        }

        botManager = new DiscordBotManager(this);
        botManager.startBot(token, channelId);

        getServer().getPluginManager().registerEvents(new MinecraftChatListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerActivityListener(this), this);

        if (getCommand("discord") != null) {
            this.getCommand("discord").setExecutor(new LinkCommand(this));
        } else {
            getLogger().warning("⚠️ Command 'discord' is not defined in plugin.yml");
        }

        getLogger().info("DiscordBridge has been enabled!");
    }

    @Override
    public void onDisable() {
        if (botManager != null) botManager.shutdown();
        getLogger().info("DiscordBridge has been disabled!");
    }

    public static DiscordBridge getInstance() {
        return instance;
    }

    public DiscordBotManager getBotManager() {
        return botManager;
    }

    public AccountLink getAccountLink() {
        return accountLink;
    }

    public WebhookManager getWebhookManager() {
        return webhookManager;
    }

    public boolean isBotReady() {
        return botManager != null && botManager.isReady();
    }
}
