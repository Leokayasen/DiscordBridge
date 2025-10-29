# DiscordBridge
DiscordBridge is a plugin that allows seamless integration between Discord and other platforms. It enables users to connect their Discord servers with Minecraft.

## Specs
- Paper 1.21.8+
- Java 17+
- Discord Java API
- Gradle for build automation and dependency management.

## Features
- Real-time message synchronization between Discord and Minecraft.
- Account linking for a unified user experience.
- Customizable settings for message formatting and notifications.
- Support for multiple Discord servers and Minecraft worlds.
- Easy setup and configuration through a user-friendly interface.

## Installation
1. Download the latest version of the DiscordBridge plugin from the releases page.
2. Place the downloaded JAR file into the `plugins` directory of your Paper server.
3. Restart your server to generate the configuration files.
4. Configure the plugin by editing the `config.yml` file located in the `plugins/DiscordBridge` directory.
5. Set up your Discord Bot in [Discord Developer Portal](https://discord.com/developers/applications) and obtain the Bot Token.
6. Copy the Bot Token into the `config.yml` file under the `discord` section.
7. Invite the bot to your Discord server using the OAuth2 URL generated in the Developer Portal.
8. Add a `channel-id` to the `config.yml` file to specify which Discord channel to sync with Minecraft.
9. Restart your server again to apply the configuration changes.
10. Enjoy seamless communication between your Discord server and Minecraft!

## Usage
- Use the `/discordbridge link` command in Minecraft to link your Minecraft account with your Discord account.
- Use the `/discordbridge unlink` command to unlink your accounts.
- Customize message formats and notification settings in the `config.yml` file.

