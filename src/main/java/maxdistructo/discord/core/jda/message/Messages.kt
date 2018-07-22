package maxdistructo.discord.core.jda.message

import maxdistructo.discord.core.jda.Client
import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.entities.*
import net.dv8tion.jda.core.requests.restaction.MessageAction
import java.time.Instant
import java.util.*

/**
 * @object Messages
 * @description Contains methods to send messages to Discord as well as a Simple Embedded Messages builder
 * @author MaxDistructo
 */

object Messages {
    var channel: PrivateChannel? = null
    fun simpleEmbed(user: Member, title: String, description: String, message: Message): MessageEmbed {
        val builder = EmbedBuilder()
        val authorAvatar = user.user.avatarUrl
        val guild = message.guild
        val color = user.color
        val guildImage = guild.iconUrl
        val guildName = guild.name
        builder.setAuthor(user.user.name + "#" + user.user.discriminator, authorAvatar)
        builder.setDescription(description)
        builder.setTitle(title)
        builder.setTimestamp(Instant.now())
        builder.setFooter(guildName, guildImage)
        builder.setColor(color)
        return builder.build()
    }

    fun sendDM(user: User, message: String): Message {
        var pm: PrivateChannel? = null
        user.openPrivateChannel()
        for (value in user.jda.privateChannels) {
            if (value.user == user) {
                pm = value
                break
            }
        }
        lateinit var builder: MessageAction
        assert(pm != null)
        builder = pm!!.sendMessage(message)
        return builder.complete(true)
    }

    fun sendDM(user: User, embed: MessageEmbed): Message {
        var pm: PrivateChannel? = null
        user.openPrivateChannel()
        for (value in user.jda.privateChannels) {
            if (value.user == user) {
                pm = value
                break
            }
        }
        lateinit var builder: MessageAction
        assert(pm != null)
        builder = pm!!.sendMessage(embed)
        return builder.complete(true)
    }

    fun sendMessage(channel: TextChannel, content: String): Message {
        val builder: MessageAction = channel.sendMessage(content)
        return builder.complete(true)
    }

    fun sendMessage(channel: MessageChannel, content: String): Message {
        val builder: MessageAction = channel.sendMessage(content)
        return builder.complete(true)
    }

    fun sendMessage(channel: TextChannel, embedded: MessageEmbed): Message {
        return channel.sendMessage(embedded).complete(true)
    }

    fun throwError(e: Exception) {
        sendDM(Client.client!!.asBot().applicationInfo.complete().owner, e.toString() + "\n" + Arrays.toString(e.stackTrace)) //General Support
    }

    fun throwError(e: Exception, message: Message) {
        sendDM(Client.client!!.asBot().applicationInfo.complete().owner, message.guild.name + "'s #" + message.channel.name + " has thrown " + e.toString() + "\n" + Arrays.toString(e.stackTrace)) //General Support
    }
}

