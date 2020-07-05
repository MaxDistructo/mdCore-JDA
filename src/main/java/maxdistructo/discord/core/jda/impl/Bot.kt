package maxdistructo.discord.core.jda.impl

import ch.qos.logback.classic.Logger
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandClient
import com.jagrosh.jdautilities.command.CommandClientBuilder
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import maxdistructo.discord.core.jda.Client
import maxdistructo.discord.core.jda.Config
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.ISnowflake
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import org.slf4j.LoggerFactory
import java.util.*

/**
 * @class Bot
 * Sets up a simple discord bot and logger. Any values that are needed to be taken out are avaliable.
 * @param token The private Discord Token for your bot.
 */

class Bot(private var token: String, private var prefix: String, private var ownerId: String) {

    constructor(token: String): this(token, Config.readPrefix(), "0")
    constructor(token: String, ownerId: String): this(token, Config.readPrefix(), ownerId)

    private var listeners : LinkedList<ListenerAdapter> = LinkedList()
    private var commands : LinkedList<Command> = LinkedList()
    private var coOwners: LinkedList<String> = LinkedList()
    private lateinit var privClient : JDA
    private var privName : String = ""
    private lateinit var privLogger : Logger
    private lateinit var privCommandClient : CommandClient
    private lateinit var commandBuilder: CommandClientBuilder
    private var intents: MutableList<GatewayIntent> = mutableListOf()

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
        commands.addAll(commandsIn)
    }

    fun setOwnerId(id: String) {
        ownerId = id
    }

    fun setOwnerId(id: Long) {
        ownerId = "" + id
    }

    fun setOwnerId(snowflake: ISnowflake) {
        ownerId = snowflake.id
    }

    fun addGatewayIntent(gatewayIntent: GatewayIntent){
        intents.add(gatewayIntent)
    }

    fun addGatewayIntents(intents: Collection<GatewayIntent>){
        this.intents.addAll(intents)
    }

    /**
     * @function init()
     * @description Starts up the bot after being setup. Allows for multiple variables to be added to the bot before it starts up.
     */
    fun init() : Bot{

        //Basic Handler setup to allow legacy support but also allow for new changes to be supported as well
        //Setup basics of the bot
        val builder = if(intents.isEmpty()){JDABuilder.createDefault(token)}else{JDABuilder.create(intents)}
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB)
        builder.setActivity(Activity.playing("Loading...."))
        //Command API Setup Code
        commandBuilder = CommandClientBuilder().useDefaultGame().setPrefix(prefix).setOwnerId("" + ownerId)
        val waiter = EventWaiter()
        commands.stream().forEach { command -> commandBuilder.addCommands(command) } //Adds all registered commands to the CommandClient
        privCommandClient = commandBuilder.build() //Builds the CommandClient
        builder.addEventListeners(waiter, commandAPI) //Adds the Command Listeners

        //Additional Listener Check
        if(listeners.isNotEmpty()){
            listeners.stream().forEach { listener -> builder.addEventListeners(listener) }
        }

        //Actual Bot Login and Startup
        privClient = builder.build().awaitReady()
        privLogger = LoggerFactory.getLogger(privClient.selfUser.name) as Logger

        //Internal API Startup Code
        Client.client = privClient
        Client.LOGGER = privLogger

        return this
    }

}
