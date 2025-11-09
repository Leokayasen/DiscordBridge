# Future Feature Suggestions

This document outlines potential features that could be added to DiscordBridge to enhance functionality and user experience.

## Overview

DiscordBridge currently provides basic bidirectional messaging between Discord and Minecraft. This document suggests additional features that would make the plugin more powerful and flexible.

## High-Priority Features

### 1. Command System Implementation

**Status:** Mentioned in README but not implemented

The README mentions `/discordbridge link` and `/discordbridge unlink` commands, but these are not currently implemented in the codebase.

**Suggested Commands:**
- `/discordbridge link` - Start account linking process
- `/discordbridge link <code>` - Complete linking with verification code
- `/discordbridge unlink` - Unlink Discord account
- `/discordbridge status` - Show connection status and linked account info
- `/discordbridge reload` - Reload configuration (admin only)
- `/discordbridge info` - Display plugin version and info

**Implementation Considerations:**
- Create a CommandExecutor class for handling commands
- Implement account linking with verification codes sent via Discord DM
- Store linked accounts in a data file (JSON or YAML)
- Add permission nodes for each command

**Benefits:**
- Completes the feature set mentioned in documentation
- Enables account-based features (role sync, name display, etc.)
- Improves user experience and plugin management

### 2. Configurable Message Formatting

**Current State:** Messages have hardcoded formats

**Proposed Enhancement:**
- Add config options for message templates
- Support placeholder variables (player name, message, timestamp, world, etc.)
- Allow color/formatting codes for both platforms
- Support different formats for different message types

**Example Configuration:**
```yaml
messages:
  minecraft-to-discord: "**{player}**: {message}"
  discord-to-minecraft: "&e[Discord] &f{author}: {message}"
  player-join: "🟢 **{player}** joined the server"
  player-quit: "🔴 **{player}** left the server"
  player-death: "💀 {deathMessage}"
  achievement: "🏆 **{player}** earned achievement: {achievement}"
```

**Benefits:**
- Customizable branding and style
- Multilingual support
- Better integration with server themes

### 3. Additional Event Notifications

**Current Events:** Chat, join, quit

**Suggested Additional Events:**
- Player deaths (with death message)
- Player achievements/advancements
- Server start/stop
- Server performance warnings (TPS drops)
- World changes
- Admin actions (kicks, bans)

**Configuration:**
```yaml
events:
  chat: true
  join: true
  quit: true
  death: true
  achievement: true
  server-start: true
  server-stop: true
  world-change: false
  admin-actions: true
```

**Benefits:**
- Better visibility into server activity
- Community engagement through achievements
- Server monitoring capabilities

## Medium-Priority Features

### 4. Discord-to-Minecraft Commands

**Concept:** Allow Discord users to execute safe commands on the server

**Suggested Implementation:**
- Whitelist of allowed commands in config
- Role-based permissions for command execution
- Command prefix in Discord (e.g., `!mc help`)
- Response messages back to Discord

**Example Configuration:**
```yaml
discord-commands:
  enabled: true
  prefix: "!mc"
  allowed-roles:
    - "Moderator"
    - "Admin"
  whitelist:
    - "list"
    - "tps"
    - "help"
    - "plugins"
```

**Example Usage:**
- `!mc list` - Show online players
- `!mc tps` - Show server TPS
- `!mc help` - Show available commands

**Security Considerations:**
- Never allow OP commands
- Validate all input
- Rate limiting per user
- Audit logging

**Benefits:**
- Remote server monitoring
- Improved moderation capabilities
- Enhanced Discord integration

### 5. Multi-Channel Support

**Current State:** Single channel for all messages

**Proposed Enhancement:**
- Support multiple Discord channels for different purposes
- Route different message types to different channels
- Separate channels for chat, events, admin actions, etc.

**Example Configuration:**
```yaml
channels:
  chat: "123456789012345678"
  events: "234567890123456789"
  admin: "345678901234567890"
  achievements: "456789012345678901"
```

**Benefits:**
- Better organization of information
- Reduced noise in main chat channel
- Specialized channels for different communities

### 6. Role-Based Permissions and Display

**Concept:** Sync Discord roles with Minecraft or use for permissions

**Features:**
- Display Discord role in Minecraft chat
- Grant Minecraft permissions based on Discord role
- Color names based on Discord role color
- Role requirements for certain features

**Example:**
- `[Admin] PlayerName: Hello` (where Admin is their Discord role)
- Only users with "Verified" role can chat
- Boosters get special perks

**Configuration:**
```yaml
role-sync:
  enabled: true
  display-role: true
  role-colors: true
  permission-mapping:
    "Discord Admin": "discordbridge.admin"
    "Discord Mod": "discordbridge.moderator"
    "Booster": "discordbridge.booster"
```

**Benefits:**
- Unified permission system
- Rewards Discord boosters
- Better community management

### 7. Message Filtering and Moderation

**Current State:** No filtering or moderation

**Proposed Enhancement:**
- Configurable word blacklist
- Regex pattern filtering
- URL filtering options
- Anti-spam protection
- Message sanitization

**Configuration:**
```yaml
filtering:
  enabled: true
  blacklist:
    - "badword1"
    - "badword2"
  allow-urls: false
  allow-mentions: true
  max-message-length: 256
  spam-protection:
    enabled: true
    max-messages-per-second: 3
    timeout-duration: 30
```

**Benefits:**
- Safer cross-platform communication
- Reduced moderation burden
- Protection against spam/abuse

### 8. Webhook Support

**Current State:** Bot posts as itself

**Proposed Enhancement:**
- Use Discord webhooks for Minecraft messages
- Display player skins as avatars
- Use player names as webhook usernames
- More native Discord appearance

**Benefits:**
- Better visual integration in Discord
- Easier to identify who's talking
- More engaging community experience

**Technical Considerations:**
- Requires webhook creation and management
- Need to handle avatar fetching (use Crafatar or similar)
- Fallback to bot messages if webhook fails

## Low-Priority / Advanced Features

### 9. Message History Sync

**Concept:** When server starts, fetch recent Discord messages and display to players

**Implementation:**
- Fetch last N messages from Discord on startup
- Display to players when they join
- Configurable history depth

**Benefits:**
- Players can see what was discussed while offline
- Better continuity of conversations

### 10. Emoji Support

**Current State:** Basic emoji passthrough

**Proposed Enhancement:**
- Convert Discord custom emoji to text names
- Convert Minecraft text emoji to Discord emoji
- Emoji reaction bridging

**Example:**
- Discord: `:custom_emoji:` → Minecraft: `[custom_emoji]`
- Minecraft: `:)` → Discord: 😊

### 11. Rich Presence / Status Updates

**Features:**
- Update Discord bot status with server info
- Show online player count
- Display server TPS or other metrics
- Rotating status messages

**Example Status:**
```
Playing on MinecraftServer | 15/50 players | TPS: 20.0
```

### 12. Attachment Handling

**Concept:** Handle Discord attachments (images, files)

**Features:**
- Notify in Minecraft when someone posts an image
- Provide short URL for viewing
- Optional file upload to Minecraft server chat

**Example:**
```
[Discord] Steve posted an image: https://discord.com/...
```

### 13. Thread Support

**Feature:** Bridge Discord threads to Minecraft

**Considerations:**
- How to represent threads in Minecraft?
- Thread-specific prefixes?
- Configuration for which threads to bridge

### 14. Voice Chat Integration

**Concept:** Bridge Discord voice chat with Minecraft proximity voice

**Note:** This would require:
- Proximity voice mod on Minecraft
- Complex audio bridging
- Significant technical challenges
- May be better as a separate plugin

### 15. Statistics and Analytics

**Features:**
- Track message counts by user
- Track online time correlation
- Popular times/days
- Export statistics to Discord

**Use Cases:**
- Community management
- Understanding player behavior
- Identifying active community members

### 16. Backup and Logging

**Features:**
- Log all bridged messages to file
- Searchable message history
- Backup before important events
- Audit trail for moderation

**Benefits:**
- Compliance and moderation
- Ability to review conversations
- Evidence for disputes

## Implementation Priority Recommendations

Based on user value and implementation complexity:

**Phase 1 (High Value, Medium Effort):**
1. Command system implementation
2. Configurable message formatting
3. Additional event notifications

**Phase 2 (Medium Value, Medium Effort):**
4. Discord-to-Minecraft commands
5. Message filtering and moderation
6. Role-based display

**Phase 3 (Medium Value, Higher Effort):**
7. Multi-channel support
8. Webhook support
9. Rich presence

**Phase 4 (Lower Priority or High Effort):**
10. Message history sync
11. Advanced emoji support
12. Statistics and analytics
13. Thread support

## Community Feedback

Consider creating a feedback mechanism:
- GitHub discussions for feature requests
- Discord server for the plugin
- In-game survey command
- User voting on priorities

## Technical Debt Considerations

Before adding major new features, consider:
- Adding unit tests
- Creating integration tests
- Refactoring for maintainability
- Documenting the API
- Creating developer documentation

## Conclusion

DiscordBridge has strong potential for growth. These features would make it one of the most comprehensive Discord integration plugins available. Prioritize based on your community's needs and your development resources.

## Contributing

If you're interested in implementing any of these features, please:
1. Open an issue to discuss the feature
2. Fork the repository
3. Implement the feature with tests
4. Submit a pull request

Community contributions are welcome!
