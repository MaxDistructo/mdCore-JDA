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

    fun simpleEmbed(user: Member, title: String, description: String, message: Message): MessageEmbed {
        return simpleEmbed(user, title, description, message.guild)
    }

    fun simpleEmbed(user: Member, title: String, description: String, guild: Guild): MessageEmbed {
        val builder = EmbedBuilder()
        val authorAvatar = user.user.avatarUrl
        val color = user.color
        val guildImage = guild.iconUrl
        val guildName = guild.name
        builder.setAuthor(user.effectiveName, authorAvatar, authorAvatar)
        builder.setDescription(description)
        builder.setTitle(title)
        builder.setTimestamp(Instant.now())
        builder.setFooter(guildName, guildImage)
        builder.setColor(color)
        return builder.build()
    }
    fun simpleEmbed(user: Member, description: String, message: Message): MessageEmbed {
        return simpleEmbed(user, description, message.guild)
    }

    fun simpleEmbed(user: Member, description: String, guild: Guild): MessageEmbed {
        val builder = EmbedBuilder()
        val authorAvatar = user.user.avatarUrl
        val color = user.color
        val guildImage = guild.iconUrl
        val guildName = guild.name
        builder.setAuthor(user.effectiveName, authorAvatar, authorAvatar)
        builder.setDescription(description)
        builder.setTimestamp(Instant.now())
        builder.setFooter(guildName, guildImage)
        builder.setColor(color)
        return builder.build()
    }
    fun sendDM(user: User, message: String): Message {
        val pm: PrivateChannel =  user.openPrivateChannel().complete()
        lateinit var builder: MessageAction
        builder = pm.sendMessage(message)
        return builder.complete(true)
    }

    fun sendDM(user: User, message: String, waitForComplete: Boolean) {
        val pm: PrivateChannel = user.openPrivateChannel().complete()
        lateinit var builder: MessageAction
        builder = pm.sendMessage(message)
        if(waitForComplete){
            builder.complete()
        }
        else{
            builder.submit()
        }
    }

    fun sendDM(user: User, embed: MessageEmbed): Message {
        val pm: PrivateChannel? = user.openPrivateChannel().complete()
        lateinit var builder: MessageAction
        assert(pm != null)
        builder = pm!!.sendMessage(embed)
        return builder.complete(true)
    }
    fun sendDM(user: User, embed: MessageEmbed, waitForComplete : Boolean) {
        val pm: PrivateChannel? = user.openPrivateChannel().complete()
        lateinit var builder: MessageAction
        assert(pm != null)
        builder = pm!!.sendMessage(embed)
        if(waitForComplete){
            builder.complete()
        }
        else{
            builder.submit()
        }
    }

    /**
     * @function sendEmbedMessage
     * @param channel : TextChannel
     * @param content : String
     *
     * Wrapper function to send a string in a simple embed
     */
    fun sendEmbedMessage(channel: TextChannel, content: String, member: Member) {
        sendMessage(channel, Messages.simpleEmbed(member, content, channel.guild))
    }

    fun sendMessage(channel: TextChannel, content: String): Message {
        val builder: MessageAction = channel.sendMessage(content)
        return builder.complete(true)
    }

    fun sendMessage(channel: TextChannel, content: String, waitForComplete : Boolean){
        val builder : MessageAction = channel.sendMessage(content)
        if(waitForComplete){
           builder.complete()
        }
        else{
            builder.submit()
        }
    }

    fun sendMessage(channel: MessageChannel, content: String): Message {
        val builder: MessageAction = channel.sendMessage(content)
        return builder.complete(true)
    }

    fun sendMessage(channel: MessageChannel, content: String, waitForComplete : Boolean){
        val builder : MessageAction = channel.sendMessage(content)
        if(waitForComplete){
            builder.complete()
        }
        else{
            builder.submit()
        }
    }

    fun sendMessage(channel: TextChannel, embedded: MessageEmbed): Message {
        return channel.sendMessage(embedded).complete(true)
    }

    fun sendMessage(channel: TextChannel, embedded: MessageEmbed, waitForComplete: Boolean) {
        val builder =  channel.sendMessage(embedded)
        if(waitForComplete){
            builder.complete()
        }
        else{
            builder.submit()
        }
    }

    fun throwError(e: Exception) {
        sendDM(Client.client!!.asBot().applicationInfo.complete().owner, e.toString() + "\n" + Arrays.toString(e.stackTrace)) //General Support
    }

    fun throwError(e: Exception, message: Message) {
        sendDM(Client.client!!.asBot().applicationInfo.complete().owner, message.guild.name + "'s #" + message.channel.name + " has thrown " + e.toString() + "\n" + Arrays.toString(e.stackTrace)) //General Support
    }
}

