package maxdistructo.discord.core.jda.command

import net.dv8tion.jda.core.entities.ChannelType
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import java.util.*

open class SimpleListener(nameIn : String) : IBaseListener() {
    open fun guildMessage(event : MessageReceivedEvent){
        println("Recieved Guild Message")
    }
    open fun privateMessage(event : MessageReceivedEvent){
        println("Recieved Private Message")
    }
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if(event.channelType == ChannelType.PRIVATE && !event.author.isBot){
            privateMessage(event)
        }
        else if (event.channelType == ChannelType.TEXT && !event.author.isBot){
            guildMessage(event)
        }
    }
    override var commandsArray: List<ICommand> = listOf()
    override var adminCommands: List<ICommand> = listOf()
    override var modCommands: List<ICommand> = listOf()
    override val name: String = nameIn
    override var commandRegistry: LinkedList<ICommand> = LinkedList()
    override fun addCommand(command: ICommand) {
        this.registerCommand(command)
    }
}