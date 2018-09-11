package maxdistructo.discord.core.jda.command

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.core.entities.Message

/**
 * @class BaseCommand
 * @description The default implementation of ICommand
 * @constructor ()
 * @constructor (commandName, type)
 * @help Added support for JDA-Utilities Command System
 */

abstract class BaseCommand() : Command() {

    final override fun execute(event: CommandEvent) { //Allows for my command style to work with the JDA-Utils version but not require commands to be rewritten
        if(!this.hasOutput) {
            event.reply(this.init(event.message, event.message.contentDisplay.split(" ")))
        }
        else{
            this.init(event.message, event.message.contentDisplay.split(" "))
        }
    }

    init{
        this.guildOnly = true
    }

    open val isEventCommand: Boolean
                get() = false

    private var type = ICommandType.NORMAL
    open val commandType: Enum<ICommandType> = type
    open val commandName : String = name
    open val requiresAdmin: Boolean
        get() = false
    open val helpMessage: String
        get() = "command <params> - A description of the command"
    open val requiresMod: Boolean
        get() = false
    open val requiresGuildOwner: Boolean
        get() = false
    open val requiresOwner: Boolean
        get() = false
    open val hasOutput : Boolean //Has Output to be outputted by the listener
        get() = true
    open val isSubcommand: Pair<Boolean, String>
        get() = Pair(false, "")

    constructor(commandNameIn: String, typeIn : Enum<ICommandType>) : this() {
        name = commandNameIn
        type = typeIn as ICommandType
        this.help = this.helpMessage
    }

    open fun init(message : Message, args : List<String>) : String {
        return ""
    }

}