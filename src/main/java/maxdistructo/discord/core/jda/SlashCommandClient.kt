package maxdistructo.discord.core.jda

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import org.apache.commons.collections4.map.LinkedMap

class SlashCommandClient(private val commandMap: LinkedMap<String, Pair<CommandData, (SlashCommandEvent) -> Any>>) : ListenerAdapter() {
    override fun onSlashCommand(event: SlashCommandEvent) {
        if(event.guild == null)
        {
            return;
        }
        for(command in commandMap)
        {
            if(command.key == event.name)
            {
                command.value.second(event)
            }
        }
    }
}