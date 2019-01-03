package maxdistructo.discord.core.jda.message

/**
 * @object Webhook
 * @description Contains methods to send webhook messages to Discord.
 * @author MaxDistructo
 * @license Copyright 2018 - MaxDistructo
 */

import net.dv8tion.jda.core.entities.Icon
import net.dv8tion.jda.core.entities.MessageEmbed
import net.dv8tion.jda.core.entities.TextChannel
import net.dv8tion.jda.webhook.WebhookClient
import net.dv8tion.jda.webhook.WebhookClientBuilder
import net.dv8tion.jda.webhook.WebhookMessageBuilder
import java.net.URL

object Webhook {

    fun send(channel: TextChannel, name: String, avatar: String, message: String) {
        val webhook = defaultWebhook(channel)
        try {
            val url = URL(avatar)
            val connection = url.openConnection()
            val userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0"
            connection.setRequestProperty("User-Agent", userAgent)
            webhook.manager.setName(name).setAvatar(Icon.from(connection.getInputStream())).complete(true)
        }
        catch(e : Exception){
            Messages.throwError(e)
        }
        val builder : WebhookClientBuilder = webhook.newClient()
        val client : WebhookClient = builder.build()
        val messageBuilder = WebhookMessageBuilder()
        messageBuilder.setContent(message)
        client.send(messageBuilder.build())
        client.close()
        webhook.delete().complete()
    }

    fun send(channel: TextChannel, message: String) {
        val webhook = defaultWebhook(channel)
        val builder : WebhookClientBuilder = webhook.newClient()
        val client : WebhookClient = builder.build()
        val messageBuilder = WebhookMessageBuilder()
        messageBuilder.setContent(message)
        client.send(messageBuilder.build())
        client.close()
        webhook.delete().complete()
    }

    fun send(webhook: net.dv8tion.jda.core.entities.Webhook, message: String) {
        val builder : WebhookClientBuilder = webhook.newClient()
        val client : WebhookClient = builder.build()
        val messageBuilder = WebhookMessageBuilder()
        messageBuilder.setContent(message)
        client.send(messageBuilder.build())
        client.close()
    }

    fun send(webhook: net.dv8tion.jda.core.entities.Webhook, vararg embeds: MessageEmbed){
        val builder : WebhookClientBuilder = webhook.newClient()
        val client : WebhookClient = builder.build()
        client.send(embeds)
        client.close()
    }

    fun defaultWebhook(channel : TextChannel) : net.dv8tion.jda.core.entities.Webhook{
        for(webhook in channel.webhooks.complete(true)){
            if(webhook.name == "bot"){
                return webhook
            }
        }
        return channel.createWebhook("bot").setAvatar(Icon.from(URL("https://www.shareicon.net/download/128x128//2017/06/21/887435_logo_512x512.png").openStream())).setName("bot").complete(true)
    }

}
