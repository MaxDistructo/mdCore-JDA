package maxdistructo.discord.core.jda.message

/**
 * @object Webhook
 * @description Contains methods to send webhook messages to Discord. This may break with any update of Discord4J cause of how it is programmed.
 * @author MaxDistructo
 * @licence Copyright 2018 - MaxDistructo
 */

import com.mashape.unirest.http.Unirest
import net.dv8tion.jda.core.entities.Channel
import net.dv8tion.jda.core.entities.Guild
import net.dv8tion.jda.core.entities.Icon
import org.json.JSONObject
import java.net.URL

object Webhook {

    fun createWebhook(channel: Channel, name: String, avatar: String) {
        RestRequestCreateWebhook(channel, name, avatar).complete(true)
    }

    private fun getWebhookFromGuild(guild: Guild, name: String): net.dv8tion.jda.core.entities.Webhook? {
        for (value in guild.webhooks.complete(true)) {
            if (value.name == name) {
                return value
            }
        }
        return null
    }

    fun name(channel: Channel, name: String, newName: String) {
        val webhook = getByName(channel, name)
        webhook!!.manager.setName(newName)
    }

    fun avatar(channel: Channel, name: String, avatar: String) {
        val webhook = getByName(channel, name)
        val stream = URL(avatar).openConnection().getInputStream()
        webhook!!.manager.setAvatar(Icon.from(stream))
        stream.close()
    }

    fun send(channel: Channel, name: String, avatar: String, message: String) {
        val webhook = defaultWebhook(channel)
        Unirest.post("https://discordapp.com/api/webhooks/" + webhook.id + "/" + webhook.token)
                .header("Content-Type", "application/json")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0")
                .body(JSONObject().put("content", message).put("avatar_url", avatar).put("username", name))
                .asJsonAsync()
    }

    fun send(channel: Channel, message: String) {
        val webhook = defaultWebhook(channel)
        Unirest.post("https://discordapp.com/api/webhooks/" + webhook.id + "/" + webhook.token)
                .header("Content-Type", "application/json")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0")
                .body(JSONObject().put("content", message))
                .asJsonAsync()

    }

    fun send(webhook: Webhook, message: String) {
        //TODO Write this method
    }

    fun getByName(channel: Channel, name: String): net.dv8tion.jda.core.entities.Webhook? {
        val webhookList = channel.guild.webhooks.complete(true)
        var webhook: net.dv8tion.jda.core.entities.Webhook? = null
        for (value in webhookList) {
            if (value.name.toLowerCase() == name.toLowerCase()) {
                webhook = value
            }
        }
        if (webhook == null) {
            createWebhook(channel, name, "")
            return getByName(channel, name)
        } else {
            return webhook
        }
    }

    fun defaultWebhook(channel: Channel): net.dv8tion.jda.core.entities.Webhook {
        return getByName(channel, "bot")!!
    }
}
