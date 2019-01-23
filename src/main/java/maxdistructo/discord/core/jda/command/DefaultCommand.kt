package maxdistructo.discord.core.jda.command

import net.dv8tion.jda.core.entities.Message

/**
 * @class DefaultCommand
 * @description A default implementations of ICommand used for GuildOwner and BotOwner commands.
 * @usage Extend a class, Override the commandName, helpMessage, hasOutput, and init(IMessage, List<String>) to create a simple command with these traits.
 */
 
object DefaultCommand {
    open class AdminCommand(name : String) : Command(){
        init{
            this.name = name
            this.guildOnly = true
            this.requiredRole = "Admin"
        }
    }
    open class BasicCommand(name : String, private val output : String) : Command(){
        init{
            this.name = name
            this.guildOnly = false //This is a basic for input, return output.
        }
        override fun execute(event : CommandEvent){
            event.reply(output).submit()
        }
    }
    open class GameCommand(name : String, guildOnly : Boolean) : Command(){
        init{
            this.name = name
            this.guildOnly = guildOnly
        }
    }
}