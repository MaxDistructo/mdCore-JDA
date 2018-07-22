package maxdistructo.discord.core.jda.command

import maxdistructo.discord.core.command.ICommandType
import net.dv8tion.jda.core.entities.Message


/**
 * @class DefaultCommand
 * @description A default implementations of ICommand used for GuildOwner and BotOwner commands.
 * @usage Extend a class, Override the commandName, helpMessage, hasOutput, and init(IMessage, List<String>) to create a simple command with these traits.
 */
object DefaultCommand {
    open class AdminCommand(val name : String) : BaseCommand(name, ICommandType.ADMIN)
    open class BasicCommand(val name : String, private val output : String) : BaseCommand(name, ICommandType.NORMAL){
        override fun init(message: Message, args: List<String>): String {
            return output
        }
    }
    open class GameCommand(val name : String) : BaseCommand(name, ICommandType.GAME)
}