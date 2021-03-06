package maxdistructo.discord.core.jda.command

import net.dv8tion.jda.core.entities.Message


/**
 * @class DefaultCommand
 * @description A default implementations of ICommand used for GuildOwner and BotOwner commands.
 * @usage Extend a class, Override the commandName, helpMessage, hasOutput, and init(IMessage, List<String>) to create a simple command with these traits.
 */
object DefaultCommand {
    open class AdminCommand(name : String) : BaseCommand(name, ICommandType.ADMIN)
    open class BasicCommand(name : String, private val output : String) : BaseCommand(name, ICommandType.NORMAL){
        override fun init(message: Message, args: List<String>): String {
            return output
        }
    }
    open class GameCommand(name : String) : BaseCommand(name, ICommandType.GAME)
}