package com.bitwave.discordbridge.commands;

import com.bitwave.discordbridge.DiscordBridge;
import com.bitwave.discordbridge.link.LinkManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class LinkCommand implements CommandExecutor {
    private final DiscordBridge plugin;
    private final LinkManager linkManager;

    public LinkCommand(DiscordBridge plugin, LinkManager linkManager) {
        this.plugin = plugin;
        this.linkManager = linkManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("&cOnly players can use this command.");
            return true;
        }

        String discordId = linkManager.getDiscordId(player.getUniqueId());
        if (discordId != null) {
            player.sendMessage("&aYour account is already linked!");
            return true;
        }

        String code = linkManager.createLinkCode(player.getUniqueId());
        player.sendMessage("&bUse &e!link " + code + " &bin Discord to finish linking.");
        return true;
    }
}
