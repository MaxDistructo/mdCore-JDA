package maxdistructo.discord.core.jda.command

import net.dv8tion.jda.core.entities.Message

/**
 * @interface ICommand
 * @description The base of all commands supported by my system. You may implement your own version of this as needed.
 * @author MaxDistructo
 */

interface ICommand{
    fun init(message : Message, args : List<String>) : String{
        return "Command Error: $commandName"
    }
    val requiresMod : Boolean
    val requiresAdmin : Boolean
    val requiresGuildOwner : Boolean
    val requiresOwner : Boolean
    val commandName : String
    val helpMessage : String
    val hasOutput : Boolean
    val commandType : Enum<ICommandType>
    val isSubcommand : Pair<Boolean, String>
}