package maxdistructo.discord.core.jda.message

/**
 * @object Webhook
 * @description Contains methods to send webhook messages to Discord.
 * @author MaxDistructo
 * @license Copyright 2018-2020 - MaxDistructo
 */

import club.minnced.discord.webhook.WebhookClient
import club.minnced.discord.webhook.send.WebhookEmbed
import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import club.minnced.discord.webhook.send.WebhookMessageBuilder
import maxdistructo.discord.core.jda.Client
import net.dv8tion.jda.api.entities.Icon
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.TextChannel
import java.net.URL

object Webhook {

    fun send(channel: TextChannel, name: String, avatar: String, message: String) {
        val webhook = defaultWebhook(channel)
        try {
            val url = URL(avatar)
            val connection = url.openConnection()
            //Have to set a UA to download avatars from even Discord itself.
            val userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0"
            connection.setRequestProperty("User-Agent", userAgent)
            webhook.manager.setName(name).setAvatar(Icon.from(connection.getInputStream())).complete(true)
        }
        catch(e : Exception){
            Messages.throwError(e)
        }
        val client : WebhookClient = WebhookClient.withId(webhook.idLong, webhook.token!!)
        val messageBuilder = WebhookMessageBuilder()
        messageBuilder.setContent(message)
        client.send(messageBuilder.build())
        client.close()
        webhook.delete().complete()
    }

    fun send(channel: TextChannel, message: String) {
        val webhook = defaultWebhook(channel)
        val client : WebhookClient = WebhookClient.withId(webhook.idLong, webhook.token!!)
        val messageBuilder = WebhookMessageBuilder()
        messageBuilder.setContent(message)
        client.send(messageBuilder.build())
        client.close()
        webhook.delete().complete()
    }

    fun send(webhook: net.dv8tion.jda.api.entities.Webhook, message: String) {
        val client : WebhookClient = WebhookClient.withId(webhook.idLong, webhook.token!!)
        val messageBuilder = WebhookMessageBuilder()
        messageBuilder.setContent(message)
        client.send(messageBuilder.build())
        client.close()
    }

    fun send(webhook : net.dv8tion.jda.api.entities.Webhook, vararg embeds: MessageEmbed){
        val outputList: MutableList<WebhookEmbed> = mutableListOf()
        embeds.forEach{e -> outputList.add(messageEmbedToWebhookEmbed(e))}
        send(webhook,outputList)
    }

    fun send(webhook: net.dv8tion.jda.api.entities.Webhook, vararg embeds: WebhookEmbed){
        //reformat vararg embed into a MutableList due to WebhookClient.send not liking the vararg format
        val outputList: MutableList<WebhookEmbed> = mutableListOf()
        embeds.forEach{e -> outputList.add(e)}
        send(webhook, outputList)
    }

    fun send(webhook: net.dv8tion.jda.api.entities.Webhook, embeds: Collection<WebhookEmbed>){
        val client : WebhookClient = WebhookClient.withId(webhook.idLong, webhook.token!!)
        //reformat vararg embed into a MutableList due to WebhookClient.send not liking the vararg format
        client.send(embeds)
        client.close()
    }

    fun defaultWebhook(channel : TextChannel) : net.dv8tion.jda.api.entities.Webhook{
        for(webhook in channel.retrieveWebhooks().complete(true)){
            if(webhook.name == Client.getApplicationInfo().name){
                return webhook
            }
        }
        //10/24/2020 - The old default icon no longer exists. F
        return channel.createWebhook("bot").setAvatar(Icon.from(URL("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Icon-round-Question_mark.svg/1200px-Icon-round-Question_mark.svg.png").openStream())).setName("bot").complete(true)
    }

    //TODO("Finish writing out this converter.")
    private fun messageEmbedToWebhookEmbed(embed : MessageEmbed) : WebhookEmbed {
        val builder = WebhookEmbedBuilder()
        when {
            embed.isEmpty -> return builder.build()
            embed.author != null -> builder.setAuthor(WebhookEmbed.EmbedAuthor(embed.author!!.name!!,embed.author!!.iconUrl, embed.author!!.url))
            embed.color != null -> builder.setColor(embed.colorRaw)
            embed.timestamp != null -> builder.setTimestamp(embed.timestamp)
        }
        return builder.build()

    }

}
