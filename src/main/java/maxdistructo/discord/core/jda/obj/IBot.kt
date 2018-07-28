package maxdistructo.discord.core.jda.obj

import ch.qos.logback.classic.Logger
import maxdistructo.discord.core.jda.command.IBaseListener
import net.dv8tion.jda.core.JDA
import java.util.*

/**
 * @interface IBot
 * @description Simple Discord Bot that implements my Listener and Command systems. May be implemented using your own features.
 * @author MaxDistructo
 */

interface IBot {

    val token : String
    val client : JDA
    val logger : Logger
    val listeners : LinkedList<IBaseListener>
    fun registerListeners()
    fun addListener(listener: IBaseListener)
    fun setToken(token : String)
    fun init()

}