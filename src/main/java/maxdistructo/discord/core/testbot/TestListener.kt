package maxdistructo.discord.core.testbot

import maxdistructo.discord.core.jda.message.Webhook
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class TestListener: ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if(event.message.author.idLong == 228111371965956099)
        {
            Webhook.send(event.message.textChannel, event.author.name, event.author.effectiveAvatarUrl, event.message.contentRaw)
        }
    }
}