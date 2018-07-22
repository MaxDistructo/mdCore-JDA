package maxdistructo.discord.core.jda.impl

import maxdistructo.discord.core.jda.command.IBaseListener
import maxdistructo.discord.core.jda.obj.IBot
import maxdistructo.discord.core.jda.priv.Client
import net.dv8tion.jda.core.JDA
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

class Bot : IBot {

    override val listeners = LinkedList<IBaseListener>()
    private var privToken : String = ""
    private lateinit var privClient : JDA

    override val token: String
        get() = privToken
    override val client: JDA
        get() = privClient
    override val logger: Logger
        get() = LoggerFactory.getLogger(client.selfUser.name)

    override fun registerListeners() {
        for(listener in listeners){
            listener.createCommands() //Must add commands to listener which grabs commands from ICommandRegistry and registers them to itself.
            privClient.addEventListener(listener)
            logger.info("Registered Listener: " + listener.name)
        }
    }

    override fun setToken(token : String){
        privToken = token
    }

    override fun init(){
        privClient = Client.createClient(privToken)!!
    }

    override fun addListener(listener: IBaseListener) {
        listeners += listener
    }
}
