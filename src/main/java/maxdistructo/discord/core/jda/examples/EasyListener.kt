package maxdistructo.discord.core.jda.examples

class EasyListener : IBaseListener(){
  
  override var commandsArray : List<ICommand>
  override var adminCommands : List<ICommand>
  override var modCommands : List<ICommand>
  override val name : String
  override var commandRegistry : LinkedList<ICommand>
  
  constructor(){
    commandArray = listOf()
    adminCommands = listOf()
    modCommands = listOf()
    name = "Listener"
    commandRegistry = 
  }
  override fun addCommand(command : ICommand){
    this.registerCommand(command)
  }

}
