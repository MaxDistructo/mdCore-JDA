package maxdistructo.discord.core.jda.command

import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import java.util.*

/**
 * @interface IBaseListener
 * @description A simple listener that is able to be registered into an JDA Client and has support for my command system
 * @author MaxDistructo
 */

abstract class IBaseListener : ListenerAdapter() {
    fun registerCommand(vararg commands : ICommand){
        for(command in commands) {
            when {
                command.requiresAdmin -> {
                    adminCommands += command
                }
                command.requiresMod -> {
                    adminCommands += command
                    modCommands += command
                }
                else -> {
                    adminCommands += command
                    modCommands += command
                    commandsArray += command
                }
            }
        }
    }

    abstract override fun onMessageReceived(event: MessageReceivedEvent)
    abstract var commandsArray : List<ICommand>
    abstract var adminCommands : List<ICommand>
    abstract var modCommands : List<ICommand>
    abstract val name : String
    abstract var commandRegistry : LinkedList<ICommand>
    abstract fun addCommand(command : ICommand)

}