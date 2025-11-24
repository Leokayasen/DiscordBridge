package com.bitwave.discordbridge;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class WebhookManager {
    private final JavaPlugin plugin;
    private final String webhookUrl;

    public WebhookManager(JavaPlugin plugin, String webhookUrl) {
        this.plugin = plugin;
        this.webhookUrl = webhookUrl;
    }

    public boolean isEnabled() {
        return webhookUrl != null && !webhookUrl.trim().isEmpty();
    }

    public void sendAchievement(Player player, String title, String description) {
        if (!isEnabled()) return;
        String avatarUrl = "https://minotar.net/helm" + player.getName() + "/64.png";
        String json = buildEmbedJson(title, description, player.getName(), avatarUrl);

        // Async HTTP post
        CompletableFuture.runAsync(() -> postJson(json));
    }

    private String buildEmbedJson(String title, String description, String author, String thumbnailUrl) {
        return "{"
                +"\"username\":\"Minecraft Achievement\","
                +"\"embeds\":[{"
                +"\"title\":\"" + escapeJson(title) + "\","
                +"\"description\":\"" + escapeJson(description) + "\","
                +"\"color\":3066993,"
                +"\"author\":{\"name\":\"" + escapeJson(author) + "\"},"
                +"\"thumbnail\":{\"url\":\"" + escapeJson(thumbnailUrl) + "\"}"
                +"}]}";
    }

    private void postJson(String json) {
        try {
            URL url = new URL(webhookUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);
            byte[] body = json.getBytes(StandardCharsets.UTF_8);
            connection.setFixedLengthStreamingMode(body.length);
            try (OutputStream ouput = connection.getOutputStream()) {
                ouput.write(body);
            }
            int code = connection.getResponseCode();
            if (code >= 400) {
                plugin.getLogger().warning("⚠️ Webhook POST return HTTP " + code);
            }
            connection.disconnect();
        } catch (Exception e) {
            plugin.getLogger().severe("⚠️ Failed to post webhook: " + e.getMessage());
        }
    }

    private String escapeJson(String string) {
        if (string == null) return "";
        return string.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
