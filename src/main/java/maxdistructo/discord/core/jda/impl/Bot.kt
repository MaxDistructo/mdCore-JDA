package maxdistructo.discord.core.jda.impl

import ch.qos.logback.classic.Logger
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandClient
import com.jagrosh.jdautilities.command.CommandClientBuilder
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.jda.Client
import maxdistructo.discord.core.jda.Config
import maxdistructo.discord.core.jda.SlashCommandClient
import maxdistructo.discord.core.jda_utils.BlacklistCommandListener
import maxdistructo.discord.core.jda_utils.Blacklisting
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.ISnowflake
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction
import org.apache.commons.collections4.map.LinkedMap
import org.slf4j.LoggerFactory
import java.util.*

/**
 * @class Bot
 * Sets up a simple discord bot and logger. Any values that are needed to be taken out are avaliable.
 * @param token The private Discord Token for your bot.
 * @param prefix The prefix for your bot
 * @param ownerId The Snowflake ID for the owner of the bot
 */

class Bot(private var token: String, private var prefix: String, private var ownerId: String) {

    constructor(token: String): this(token, Config.readPrefix(), "0")
    constructor(token: String, ownerId: String): this(token, Config.readPrefix(), ownerId)

    private var listeners : LinkedList<ListenerAdapter> = LinkedList()
    private var commands : LinkedList<Command> = LinkedList()
    private var coOwners: LinkedList<String> = LinkedList()
    private var slashCommands : LinkedMap<String, Pair<CommandData, (SlashCommandEvent) -> Any>> = LinkedMap()
    private lateinit var privClient : JDA
    private var privName : String = ""
    private lateinit var privLogger : Logger
    private lateinit var privCommandClient : CommandClient
    private lateinit var commandBuilder: CommandClientBuilder
    private lateinit var privSlashCommandClient : CommandListUpdateAction
    private var intents: MutableList<GatewayIntent> = mutableListOf()
    var initalized: Boolean = false
    private var jda_utils = true

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
    fun registerSlashCommand(name: String, data: CommandData, runFunction: (SlashCommandEvent) -> Any) {
        slashCommands[name] = Pair(data, runFunction)
        if(initalized && slashCommands.size <= 100) {
            privSlashCommandClient.addCommands(data)
            privSlashCommandClient.queue()
        }
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
     * Run this to disable the setup of JDA-Utilities or it's forks such as Chewtils
     */
    fun disableJDAUtils(){
        jda_utils = false;
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
        if(jda_utils) {
            commandBuilder = CommandClientBuilder().useDefaultGame().setPrefix(prefix).setOwnerId("" + ownerId)
            val waiter = EventWaiter()
            commands.stream()
                .forEach { command -> commandBuilder.addCommands(command) } //Adds all registered commands to the CommandClient
            commandBuilder.setListener(BlacklistCommandListener(Utils.convertToLong(ownerId)!!))
            commandBuilder.addCommands(Blacklisting.CommandBan(), Blacklisting.CommandUnban())
            privCommandClient = commandBuilder.build() //Builds the CommandClient
            builder.addEventListeners(waiter, commandAPI) //Adds the Command Listeners
        }
        else{
            //TODO: Implement non JDA-Utils interface for the Blacklisting features.
        }

        //SlashCommand API Setup
        builder.addEventListeners(SlashCommandClient(slashCommands)) //Pass the slash commands that have been registered to the Listener

        //Additional Listener Check
        if(listeners.isNotEmpty()){
            listeners.stream().forEach { listener -> builder.addEventListeners(listener) }
        }

        //Actual Bot Login and Startup
        privClient = builder.build().awaitReady()
        privLogger = LoggerFactory.getLogger(privClient.selfUser.name) as Logger

        //Since we need the JDA client to be ready BEFORE we register the slash commands, finish the registration here
        privSlashCommandClient = privClient.updateCommands()
        for(command in slashCommands)
        {
            privSlashCommandClient.addCommands(command.value.first)
        }
        privSlashCommandClient.queue()

        //Set a flag so that any new slash commands created automatically get added to the bot even if created after init.
        initalized = true

        //Internal API Startup Code
        Client.client = privClient
        Client.LOGGER = privLogger

        //We've finished init, set ourselves online. This should be almost immediate depending on network/host speed
        privClient.presence.setStatus(OnlineStatus.ONLINE)
        privClient.presence.activity = Activity.playing(prefix + "help")

        return this
    }

}
