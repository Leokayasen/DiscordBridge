# DiscordBridge
DiscordBridge is a plugin that allows seamless integration between Discord and other platforms. It enables users to connect their Discord servers with Minecraft.

## Version 1.2.6 - Performance Release
This release focuses on minor formatting and performance imrpovements.\
**Upgrade Note**: 100% backward compatible.

**What Changed**
- Added `AccountLink.java` to centralize account linking logic.
- Added `LinkCommand.java` to handle the `/discord` command.
- Added `WebhookManager.java` to manage webhook sending.
- Updated `DiscordMessageListener.java` to see DMs for account linking.
- Updated `config.yml` with a `webhook-url` option for enabling the webhook feature.
  - This allows achievements from Minecraft to be sent to Discord via webhooks.


## Installation
> [!WARNING]
> You MUST keep the config.yml file as provided, unless you are editing the file via your Minecraft server host.\
> This is because, if your version of the code is hosted on GitHub, publishing your personal TOKEN for a bot is a security hazard and not advised.

1. Download the latest version of the DiscordBridge plugin from the releases page.
2. Place the downloaded JAR file into the `plugins` directory of your Paper server.
3. Restart your server to generate the configuration files.
4. Configure the plugin by editing the `config.yml` file located in the `plugins/DiscordBridge` directory.
5. Set up your Discord Bot in [Discord Developer Portal](https://discord.com/developers/applications) and obtain the Bot Token.
6. Copy the Bot Token into the `config.yml` file under the `discord` section.
7. Invite the bot to your Discord server using the OAuth2 URL generated in the Developer Portal.
8. Add a `channel-id` to the `config.yml` file to specify which Discord channel to sync with Minecraft.
9. Optionally, include a `webhook-url` in the `config.yml` to enable webhook features for achievements.
10. Restart your server again to apply the configuration changes.
11. Enjoy seamless communication between your Discord server and Minecraft!



## Usage
- Use the `/discord` command in Minecraft to link your Minecraft account with your Discord account.
  - In-game, the plugin will generate a unique code.
  - Send the code to the bot in DMs, to complete linking.

## Support
For support, you can join our [Discord Server](https://discord.gg/2qDFMGb9Eb) or open an issue on the GitHub repository.
