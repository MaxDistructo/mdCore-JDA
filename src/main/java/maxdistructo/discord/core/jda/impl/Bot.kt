package maxdistructo.discord.core.jda.impl

import ch.qos.logback.classic.Logger
import maxdistructo.discord.core.jda.command.IBaseListener
import maxdistructo.discord.core.jda.obj.IBot
import maxdistructo.discord.core.jda.priv.Client
import net.dv8tion.jda.core.JDA
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
        get() = LoggerFactory.getLogger(client.selfUser.name) as Logger

    override fun registerListeners() {
        for(listener in listeners){
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
