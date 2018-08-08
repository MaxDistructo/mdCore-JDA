package maxdistructo.discord.core.jda.message

/**
 * @object Webhook
 * @description Contains methods to send webhook messages to Discord.
 * @author MaxDistructo
 * @licence Copyright 2018 - MaxDistructo
 */

import com.mashape.unirest.http.Unirest
import net.dv8tion.jda.core.entities.Icon
import net.dv8tion.jda.core.entities.MessageEmbed
import net.dv8tion.jda.core.entities.TextChannel
import net.dv8tion.jda.webhook.WebhookClient
import net.dv8tion.jda.webhook.WebhookClientBuilder
import net.dv8tion.jda.webhook.WebhookMessageBuilder
import org.json.JSONObject
import java.net.URL

object Webhook {

    fun send(channel: TextChannel, name: String, avatar: String, message: String) {
        val webhook = defaultWebhook(channel)
        Unirest.post("https://discordapp.com/api/webhooks/" + webhook.id + "/" + webhook.token)
                .header("Content-Type", "application/json")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0")
                .body(JSONObject().put("content", message).put("avatar_url", avatar).put("username", name))
                .asJsonAsync()
    }

    fun send(channel: TextChannel, message: String) {
        val webhook = defaultWebhook(channel)
        Unirest.post("https://discordapp.com/api/webhooks/" + webhook.id + "/" + webhook.token)
                .header("Content-Type", "application/json")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0")
                .body(JSONObject().put("content", message))
                .asJsonAsync()
    }

    fun send(webhook: net.dv8tion.jda.core.entities.Webhook, message: String) {
        val builder : WebhookClientBuilder = webhook.newClient()
        val client : WebhookClient = builder.build()
        val messageBuilder = WebhookMessageBuilder()
        messageBuilder.setContent(message)
        client.send(messageBuilder.build())
    }

    fun send(webhook: net.dv8tion.jda.core.entities.Webhook, vararg embeds: MessageEmbed){
        val builder : WebhookClientBuilder = webhook.newClient()
        val client : WebhookClient = builder.build()
        client.send(embeds)
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
