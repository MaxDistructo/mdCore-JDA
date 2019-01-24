package maxdistructo.discord.core.jda.impl

import ch.qos.logback.classic.Logger
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandClient
import com.jagrosh.jdautilities.command.CommandClientBuilder
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import maxdistructo.discord.core.jda.Client
import maxdistructo.discord.core.jda.Config
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.OnlineStatus
import net.dv8tion.jda.core.entities.Game
import net.dv8tion.jda.core.hooks.ListenerAdapter
import org.slf4j.LoggerFactory
import java.util.*

/**
 * @class Bot
 * Sets up a simple discord bot and logger. Any values that are needed to be taken out are avaliable.
 * @param token The private Discord Token for your bot.
 * @param ownerId The Debug ID of the bot owner.
 */

class Bot(private val token : String, val ownerId : Long)  {

    private var listeners : LinkedList<ListenerAdapter> = LinkedList()
    private var commands : LinkedList<Command> = LinkedList()
    private lateinit var privClient : JDA
    private var privName : String = ""
    private lateinit var privLogger : Logger
    private lateinit var privCommandClient : CommandClient
    private var commandBuilder : CommandClientBuilder = CommandClientBuilder().useDefaultGame().setPrefix(Config.readPrefix()).setOwnerId("" + ownerId)

    val commandAPI : CommandClient
        get() = privCommandClient

    val client: JDA
        get() = privClient
    val logger: Logger
        get() = privLogger

    fun registerListener(listener : ListenerAdapter){
        listeners.add(listener)
    }
    fun registerListeners(vararg listenersIn: ListenerAdapter){
        for(listener in listenersIn){
            listeners.add(listener)
        }
    }
    fun registerCommand(command : Command){
        commands.add(command)
    }
    fun registerCommands(vararg commandsIn : Command){
        for(command in commandsIn){
            commands.add(command)
        }
    }

    /**
     * @function init()
     * @description Starts up the bot after being setup. Allows for multiple variables to be added to the bot before it starts up.
     */
    fun init() : Bot{

        //Basic Bot Setup Code
        val builder = JDABuilder(AccountType.BOT)
        builder.setToken(token)
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB)
        builder.setGame(Game.playing("Loading..."))

        //Command API Setup Code
        val waiter = EventWaiter()
        addCommands() //Adds all registered commands to the CommandClient
        privCommandClient = commandBuilder.build() //Builds the CommandClient
        builder.addEventListener(waiter, commandAPI) //Adds the Command Listeners

        //Additional Listener Check
        if(listeners.isNotEmpty()){
         registerListeners(builder)
        }

        //Actual Bot Login and Startup
        privClient = builder.build().awaitReady()
        privLogger = LoggerFactory.getLogger(privClient.selfUser.name) as Logger

        //Internal API Startup Code
        Client.client = privClient
        Client.LOGGER = privLogger

        return this
    }
    private fun addCommands(){
        for(command in commands){
            commandBuilder.addCommand(command)
        }
    }
    private fun registerListeners(builder : JDABuilder) : JDABuilder {
        for (listener in listeners) {
            builder.addEventListener(listener)
        }
        return builder
    }

}
