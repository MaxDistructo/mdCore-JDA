package maxdistructo.discord.core.jda.examples

import maxdistructo.discord.core.jda.command.IBaseListener
import maxdistructo.discord.core.jda.command.ICommand
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import java.util.*

abstract class EasyListener : IBaseListener{
  override fun onMessageReceived(event: MessageReceivedEvent) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override var commandsArray : List<ICommand>
  override var adminCommands : List<ICommand>
  override var modCommands : List<ICommand>
  override val name : String
  override var commandRegistry : LinkedList<ICommand>

  constructor(){
    commandsArray = listOf<ICommand>()
    adminCommands = listOf()
    modCommands = listOf()
    name = "Listener"
    commandRegistry = LinkedList()
  }
  override fun addCommand(command : ICommand){
    this.registerCommand(command)
  }

}
