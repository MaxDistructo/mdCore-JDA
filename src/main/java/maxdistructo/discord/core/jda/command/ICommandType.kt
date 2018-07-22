package maxdistructo.discord.core.command

/**
 * @enum ICommandType
 * @description Used to distinguish if a command is able to be ran in the specific channel. Eventually will be integrated with perm system.
 * @author MaxDistructo
 */
enum class ICommandType {
    NORMAL,
    ADMIN,
    GAME
}