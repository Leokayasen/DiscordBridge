package com.bitwave.discordbridge.utility;

import com.bitwave.discordbridge.DiscordBridge;

public class MessageFormatter {
    public static String format(String path, String player, String message) {
        String template = DiscordBridge.getInstance().getConfig().getString(path, "{player}: {message}");
        return template
                .replace("{player}", player != null ? player : "")
                .replace("{message}", message != null ? message : "")
                .replace("&", "§"); // Allow color codes in config
    }
}
