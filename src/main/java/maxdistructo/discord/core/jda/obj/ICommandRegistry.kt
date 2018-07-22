package maxdistructo.discord.core.jda.obj

import maxdistructo.discord.core.jda.command.IBaseListener

interface ICommandRegistry {
    /**
     * The following function is called upon IDiscordBot initializing the listener.
     * @param listener The listener to register the commands to
     */
    fun registerCommands(listener : IBaseListener)
}