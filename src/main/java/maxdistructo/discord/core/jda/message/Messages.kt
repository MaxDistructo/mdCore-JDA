package maxdistructo.discord.core.jda.message

import maxdistructo.discord.core.jda.Client
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.*
import net.dv8tion.jda.api.requests.restaction.MessageAction
import java.time.Instant
import java.util.*

/**
 * @object Messages
 * @description Contains methods to send messages to Discord as well as a Simple Embedded Messages builder
 * @author MaxDistructo
 */

object Messages {

    /**
     *
     * Creates an embed with the provided parameters. This is essentially the simpleEmbed but with a title
     * @param user The User who we want to be the author of this embed.
     * @param title The title of the embeded message
     * @param description The description on the embed
     * @param message The message that caused us to create this embed. Used to pull other values.
     *
     */
    fun simpleTitledEmbed(user: Member, title: String, description: String, message: Message): MessageEmbed {
        return simpleTitledEmbed(user, title, description, message.guild)
    }

    /**
     *
     * Creates an embed with the provided parameters. This is essentially the simpleEmbed but with a title
     * @param user The User who we want to be the author of this embed.
     * @param title The title of the embeded message
     * @param description The description on the embed
     * @param guild The Guild object of the guild we will be putting this embed in.
     *
     */

    fun simpleTitledEmbed(user: Member, title: String, description: String, guild: Guild): MessageEmbed {
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

    /**
     *
     * Creates a simple Embeded message to send. We don't want to go overkill on this but we want to make it look not
     * horrid by personalizing the message to the guild and user who this embed is a reply to.
     *
     * @param user The User who we want to be the author of this embed.
     * @param description The description on the embed
     * @param message The message that caused us to create this embed. Used to pull other values.
     *
     */

    fun simpleEmbed(user: Member, description: String, message: Message): MessageEmbed {
        return simpleEmbed(user, description, message.guild)
    }

    /**
     *
     * Creates a simple Embeded message to send. We don't want to go overkill on this but we want to make it look not
     * horrid by personalizing the message to the guild and user who this embed is a reply to.
     *
     * @param user The User who we want to be the author of this embed.
     * @param description The description on the embed
     * @param guild The guild where we will be sending this embed after creation. Could be another guild that the bot
     * is in if you wish to cross post for whatever reason.
     *
     */

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

    /**
     *
     * Sends a Direct Message to the Member/User we specify. This allows us to just ask for a message to be sent
     * with only the information we need to send it.
     *
     * @param member The Member who we want to send a DM to
     * @param message The message we want to send to that user.
     *
     */

    fun sendDM(member: Member, message: String): Message{
        return sendDM(member.user, message);
    }

    /**
     *
     * Sends a Direct Message to the Member/User we specify. This allows us to just ask for a message to be sent
     * with only the information we need to send it.
     *
     * @param user The User who we want to send a DM to
     * @param message The message we want to send to that user.
     *
     */

    fun sendDM(user: User, message: String): Message {
        return sendDM(user, message, true)
    }

    /**
     *
     * Sends a Direct Message to the Member/User we specify. This allows us to just ask for a message to be sent
     * with only the information we need to send it. This also has an optional parameter to wait for the message to be
     * sent before returning, blocking execution.
     *
     * @param user The Member who we want to send a DM to
     * @param message The message we want to send to that user.
     *
     */

    fun sendDM(user: User, message: String, waitForComplete: Boolean) : Message {
        val pm: PrivateChannel = user.openPrivateChannel().complete()
        lateinit var builder: MessageAction
        builder = pm.sendMessage(message)
        return if(waitForComplete){
            builder.complete(true)
        }
        else{
            builder.submit().get()
        }
    }

    fun sendDM(member: Member, embed: MessageEmbed): Message{
        return sendDM(member.user, embed)
    }

    fun sendDM(user: User, embed: MessageEmbed): Message {
        return sendDM(user, embed, true)
    }


    fun sendDM(user: User, embed: MessageEmbed, waitForComplete : Boolean) : Message {
        val pm: PrivateChannel? = user.openPrivateChannel().complete()
        lateinit var builder: MessageAction
        assert(pm != null)
        builder = pm!!.sendMessageEmbeds(embed)
        return if(waitForComplete){
            builder.complete(true)
        }
        else{
            builder.submit().get()
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
        return channel.sendMessageEmbeds(embedded).complete(true)
    }

    fun sendMessage(channel: TextChannel, embedded: MessageEmbed, waitForComplete: Boolean) {
        val builder =  channel.sendMessageEmbeds(embedded)
        if(waitForComplete){
            builder.complete()
        }
        else{
            builder.submit()
        }
    }

    fun throwError(e: Exception) {
        sendDM(Client.client!!.retrieveApplicationInfo().complete().owner, e.toString() + "\n" + Arrays.toString(e.stackTrace)) //General Support
    }

    fun throwError(e: Exception, message: Message) {
        sendDM(Client.client!!.retrieveApplicationInfo().complete().owner, message.guild.name + "'s #" + message.channel.name + " has thrown " + e.toString() + "\n" + Arrays.toString(e.stackTrace)) //General Support
    }
}

