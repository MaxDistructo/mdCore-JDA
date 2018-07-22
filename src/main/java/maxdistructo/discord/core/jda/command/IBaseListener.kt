package maxdistructo.discord.core.jda.command

import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import java.util.*

/**
 * @interface IBaseListener
 * @description A simple listener that is able to be registered into an JDA Client and has support for my command system
 * @author MaxDistructo
 */

abstract class IBaseListener : ListenerAdapter() {
    fun registerCommand(vararg commands : BaseCommand){
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

    @Override
    abstract fun onMessageReceivedEvent(event : MessageReceivedEvent)
    abstract var commandsArray : List<BaseCommand>
    abstract var adminCommands : List<BaseCommand>
    abstract var modCommands : List<BaseCommand>
    abstract val name : String

    abstract var commandRegistry : LinkedList<ICommand>
    abstract fun addCommand(command : ICommand)
    abstract fun createCommands()

}