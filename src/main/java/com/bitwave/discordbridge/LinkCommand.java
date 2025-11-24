package com.bitwave.discordbridge;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

/**
 * Usage: /discord
 */
public class LinkCommand implements CommandExecutor {
    private final DiscordBridge plugin;

    public LinkCommand(DiscordBridge plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        String code = plugin.getAccountLink().generateCode(player);
        player.sendMessage("Your link code is: " + code + " - DM the bot to complete linking.");
        return true;
    }

}
