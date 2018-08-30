package maxdistructo.discord.core.jda.examples

import maxdistructo.discord.core.jda.command.IBaseListener
import maxdistructo.discord.core.jda.command.ICommand
import maxdistructo.discord.core.jda.obj.ICommandRegistry
import java.util.*

class EasyCommandRegistry : ICommandRegistry{

  var commands = LinkedList<ICommand>()

  override fun registerCommands(listener : IBaseListener){
    for(command in commands){
      listener.registerCommand(command)
    }
  }

  fun addCommand(command : ICommand){
    commands.add(command)
  }

}
