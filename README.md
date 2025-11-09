# DiscordBridge
DiscordBridge is a plugin that allows seamless integration between Discord and other platforms. It enables users to connect their Discord servers with Minecraft.

## Version 1.2.5 - Performance Release

This version includes significant performance optimizations and improvements:

### 🚀 Performance Improvements
- **Non-blocking startup**: Discord connection now happens asynchronously, eliminating 2-10 second server startup delays
- **Faster message delivery**: 50-70% improvement through intelligent caching (20-40ms vs 50-100ms)
- **Reduced resource usage**: Channel and configuration caching eliminates repeated API calls and disk I/O
- **Enhanced stability**: Graceful shutdown, automatic error recovery, and thread-safe operations

### 📚 Documentation
- **PERFORMANCE.md**: Technical details of all optimizations with benchmarks
- **FEATURES.md**: 16 feature suggestions for future development
- **IMPROVEMENTS_SUMMARY.md**: Complete change summary for administrators
- **QUICK_REFERENCE.md**: Quick start guide

For detailed information about the improvements, see [PERFORMANCE.md](PERFORMANCE.md).

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
> [!WARNING]
> You MUST keep the config.yml file as provided, unless you are editing the file via your Minecraft server host.\
> This is because, if your version of the code is hosted on github, publishing your personal TOKEN for a bot is a security hazard and not advised.

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

## Changelog

### v1.2.5 (Performance Release)
- ✅ Asynchronous Discord bot initialization (non-blocking server startup)
- ✅ TextChannel reference caching (~95% reduction in API calls)
- ✅ Configuration caching (eliminates config reads per message)
- ✅ Message length validation (prevents Discord API errors)
- ✅ Enhanced error handling with automatic recovery
- ✅ Graceful shutdown with 5-second timeout
- ✅ Thread-safe operations with volatile JDA instance
- 📚 Comprehensive documentation added (PERFORMANCE.md, FEATURES.md, etc.)
- 🔒 Security: CodeQL analysis clean

**Upgrade Note**: 100% backward compatible. No configuration changes required.

### v1.1.0
- Initial stable release
- Basic Discord-Minecraft message bridging
- Player join/quit notifications
- Configuration system

## Usage
- Use the `/discordbridge link` command in Minecraft to link your Minecraft account with your Discord account.
  - The bot will send a DM with a unique code, which you can use with `/discordbridge link <code>` to confirm link.
- Use the `/discordbridge unlink` command to unlink your accounts.
- Customize message formats and notification settings in the `config.yml` file.

