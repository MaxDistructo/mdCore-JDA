package maxdistructo.discord.core.jda

import maxdistructo.discord.core.Utils
import net.dv8tion.jda.api.entities.GuildChannel
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.User

object JDAUtils {
    /**
     *   Gets the mentioned channel from the IMessage
     *   @param message The message
     *   @return The mentioned channel or null if impossible
     */

    fun getMentionedChannel(message: Message): GuildChannel? {
        val mentionedChannelList = message.mentionedChannels
        val mentionedChannelArray = mentionedChannelList.toTypedArray()
        return if (mentionedChannelArray.isNotEmpty()) {
            mentionedChannelArray[0]
        } else {
            null
        }
    }

    /**
     *   Gets the mentioned user from the IMessage
     *   @param message The message
     *   @return The mentioned user or null if impossible
     */

    fun getMentionedUser(message : Message) : Member?{
        return getMentionedUser(message, 0)
    }

    fun getMentionedUser(message: Message, mentionNum : Int): Member? {
        val mentionedList = message.mentionedMembers
        val mentionedArray = mentionedList.toTypedArray()
        val mentioned: Member?
        if (mentionedArray.isNotEmpty()) {
            mentioned = mentionedList[mentionNum]
        } else {
            mentioned = null
        }
        return mentioned
    }

    /**
     *   Gets the attachment from the IMessage
     *   @param message The message
     *   @return The attachment or null if impossible
     */

    fun getAttachement(message: Message): Message.Attachment? {
        val attachments: List<Message.Attachment>? = message.attachments
        return attachments!![0]
    }

    /**
     *   Gets the url for the attachment
     *   @param message The message
     *   @return The url for the attachement or null if impossible
     */

    fun getAttachementUrl(message: Message): String {
        val attachments: List<Message.Attachment>? = message.attachments
        return attachments!![0].url
    }

    /**
     *   Attempts to get a user from the input if a mentioned user is not found. This is accomplished by comparing the input to the Debug ID of a user, the actual Account name of the User, and finally checking if it is a fragment of the name of the user.
     *   @param message The message
     *   @param input The name/longID/namefragement for the user
     *   @return The user that was mentioned or matches one of the characteristics
     */

    fun getUserFromInput(message : Message, input: Any) : Member?{
        return getUserFromInput(message, input, 0)
    }

    /**
     * Extended variant of getUserFromInput for multiple mentions in a single message
     * @param message The Message
     * @param input The name/longID/namefragment for the user
     * @param mentionNum The mention number in the message.
     * @return The user that was mentioned or matches one of the characteristics
     */
    fun getUserFromInput(message: Message, input: Any, mentionNum : Int): Member? {
        when {
            getMentionedUser(message) != null -> return getMentionedUser(message, mentionNum)
            Utils.convertToLong(input) != null -> return message.guild.getMemberById(Utils.convertToLong(input)!!)
            message.guild.getMembersByEffectiveName(input.toString(), true).isNotEmpty() -> return message.guild.getMembersByEffectiveName(input.toString(), true)[0]
            else ->
                for (user in message.guild.members) {
                    if (user.effectiveName.contains(input.toString())) {
                        return user
                    }
                }
        }
        return null
    }

    /**
     *   Similar to the above method but does not have the message requirement and as such can not check as much
     *   @param input The name/longID of a user
     *   @return The user
     */

    fun getUserFromInput(input: Any): User? {
        when {
            Utils.convertToLong(input) != null -> return Client.client!!.getUserById(Utils.convertToLong(input)!!)
            Client.client!!.getUsersByName(input.toString(), true).isNotEmpty() -> return Client.client!!.getUsersByName(input.toString(), true)[0]
        }
        return null
    }
}