package maxdistructo.discord.core.jda

import maxdistructo.discord.core.jda.Client.client
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.*

/**
 * @object Perms
 * @description This class uses the local config files to determine if someone is an Admin or Moderator of the server/guild.
 * @author MaxDistructo
 */

object Perms{
    fun checkMod(message: Message): Boolean {
        val author = message.author
        //Checks if user is a Moderator of the server
        val moderators = Config.readServerModConfig(message.guild)
        var i = 0
        while (i < moderators.size) {
            if (author.idLong == moderators[i] || checkAdmin(message) || checkOwner_Guild(message)) {
                return true
            }
            i++
        }
        return false
    }

    fun checkAdmin(message: Message): Boolean {
        val author = message.author
        //Checks if user is a Admin/Owner of the Server (Or Myself).
        val admins = Config.readServerAdminConfig(message.guild)
        var i = 0
        while (i < admins.size) {
            if (author.idLong == admins[i] || checkOwner_Guild(message)) {
                return true
            }
            i++
        }
        return false
    }

    fun checkOwner_Guild(message: Message): Boolean {
        val author = message.author

        return author.idLong == message.guild.owner!!.user.idLong || checkOwner(message)
    }

    fun checkOwner(message: Message): Boolean {
        val author = message.author
        return author.idLong == Client.getApplicationInfo().owner.idLong
    }

    fun checkGames(message: Message): Boolean {
        val channel = message.channel
        val channelName = channel.name

        val channels = Config.readServerGamesConfig(message.guild)
        var i = 0
        while (i < channels.size) {
            if (channelName == channels[i]) {
                return true
            }
            i++
        }
        return false
    }

    fun checkForPermission(message: Message, permission: Permission): Boolean {
        return message.member!!.hasPermission(permission)
    }
}
