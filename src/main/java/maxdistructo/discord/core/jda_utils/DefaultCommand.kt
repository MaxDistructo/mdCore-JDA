package maxdistructo.discord.core.jda_utils
object DefaultCommand {} //Holder for later updates
/**
package maxdistructo.discord.core.jda_utils

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
/**
 * @class DefaultCommand
 * @description Basic command setups of common command types I use.
 * @usage Extend a class, Override the commandName, helpMessage, hasOutput, and init(IMessage, List<String>) to create a simple command with these traits.
 */
 
object DefaultCommand {
    abstract class AdminCommand(name : String) : Command(){
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
            event.reply(output)
        }
    }
    abstract class GameCommand(name : String, guildOnly : Boolean) : Command(){
        init{
            this.name = name
            this.guildOnly = guildOnly
        }
    }
}
 **/