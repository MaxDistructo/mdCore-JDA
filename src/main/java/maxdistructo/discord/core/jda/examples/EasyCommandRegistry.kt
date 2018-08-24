package maxdistructo.discord.core.jda.examples

class EasyCommandRegistry : ICommandRegistry(){

  var commands = LinkedList<ICommand>()

  override fun registerCommands(listener : IBaseListener){
    for(command in commands){
      listener.registerCommand(command)
    }
  }

  fun addCommand(command : ICommand){
    commands += command
  }

}
