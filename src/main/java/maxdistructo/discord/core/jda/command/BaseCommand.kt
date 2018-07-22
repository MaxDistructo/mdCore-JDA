package maxdistructo.discord.core.jda.command

import maxdistructo.discord.core.command.ICommandType

open class BaseCommand() : ICommand {

    private var name = "command"
    private var type = ICommandType.NORMAL

    override val commandType: Enum<ICommandType>
        get() = type
    override val commandName : String
        get() = name
    override val requiresAdmin: Boolean
        get() = false
    override val helpMessage: String
        get() = "command <params> - A description of the command"
    override val requiresMod: Boolean
        get() = false
    override val requiresGuildOwner: Boolean
        get() = false
    override val requiresOwner: Boolean
        get() = false
    override val hasOutput : Boolean
        get() = true

    constructor(commandNameIn: String, typeIn : Enum<ICommandType>) : this() {
        name = commandNameIn
        type = typeIn as ICommandType
    }

    fun register(listener : IBaseListener) : BaseCommand {
        listener.registerCommand(this)
        return this
    }
}