package maxdistructo.discord.core.jda.obj

import maxdistructo.discord.core.jda.command.IBaseListener
import net.dv8tion.jda.core.JDA
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

interface IDiscordBot {
    var client : JDA
    var logger : Logger
    var bot : IBot
    var registry : LinkedList<Pair<ICommandRegistry, IBaseListener>>
    /**
     * Allows the user to register a ICommandRegistry(Command Class Holder) to a IBaseListener and the IBaseListener to the bot in 1 function
     * @param commandRegistry The Command Registry that holds all the commands
     * @param listener The Listener to register the commands in the command registry to
     */
    fun registerCommandRegistry(commandRegistry : ICommandRegistry, listener : IBaseListener){
        registry.add(Pair(commandRegistry, listener))
    }
    fun init(botClass : Class<IBot>){
        bot = botClass.newInstance()
        client = bot.client
        for(value in registry){
            value.first.registerCommands(value.second)
            bot.addListener(value.second)
        }
        logger = LoggerFactory.getLogger(this::class.java)
        bot.init()
    }
}