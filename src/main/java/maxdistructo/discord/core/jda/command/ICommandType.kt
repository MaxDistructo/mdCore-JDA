package maxdistructo.discord.core.jda.command

/**
 * @enum ICommandType
 * @description Used to distinguish if a command is able to be ran in the specific channel. Eventually will be integrated with perm system.
 * @author MaxDistructo
 */

@Deprecated("Use JDAUtils Command API") // TODO("Remove in 2.3 Release of mdCore-JDA")
enum class ICommandType {
    NORMAL,
    ADMIN,
    GAME
}