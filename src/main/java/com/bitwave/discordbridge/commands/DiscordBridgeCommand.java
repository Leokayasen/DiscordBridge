package com.bitwave.discordbridge.commands;

import com.bitwave.discordbridge.DiscordBridge;
import org.bukkit.command.*;

public class DiscordBridgeCommand implements CommandExecutor {
    private final DiscordBridge plugin;

    public DiscordBridgeCommand(DiscordBridge plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§6DiscordBridge §7- /discordbridge reload | test");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                plugin.reloadConfig();
                sender.sendMessage("§aDiscordBridge Config reloaded.");
            }
            case "test" -> {
                if (plugin.getBotManager() != null) {
                    plugin.getBotManager().sendMessage("✅ DiscordBridge test message from Minecraft.");
                    sender.sendMessage("§aTest message sent to Discord.");
                } else {
                    sender.sendMessage("§cDiscord bot is not active.");
                }
            }
            default -> sender.sendMessage("§cUnknown subcommand.");
        }
        return true;
    }
}
