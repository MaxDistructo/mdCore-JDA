package maxdistructo.discord.core.jda

import maxdistructo.discord.core.jda.message.Messages
import net.dv8tion.jda.core.entities.Guild
import net.dv8tion.jda.core.entities.Member
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.Role
import java.awt.Color

/**
 * @object Roles
 * @description This class contains methods to get and modify discord roles.
 * @author MaxDistructo
 */

object Roles {
    fun getRole(message: Message, role: String): Role? {
        val roles = message.guild.getRolesByName(role, true)
        return if (roles.size != 0) {
            roles[0]
        } else {
            null
        }
    }

    fun copyRolePerms(copy: Role, paste: Role) {
        val permissions = copy.permissions
        paste.permissions.addAll(permissions)
        paste.manager.givePermissions(permissions).complete(true)
    }

    fun applyRole(message: Message, mentioned: Member, role: String) {
        val roleList = message.guild.getRolesByName(role, true)
        if (roleList.isEmpty()) {
            Messages.sendMessage(message.channel, "The role $role was not found.")
            Thread.interrupted()
        }
        if (!roleList.isEmpty()) {
            mentioned.guild.controller.addSingleRoleToMember(mentioned, roleList[0]).complete(true)
        }
    }

    fun applyRole(message: Message, mentioned: Member, roleL: Long) {
        val role = message.guild.getRoleById(roleL)
        if (role == null) {
            Messages.sendMessage(message.channel, "The role $roleL was not found.")
            Thread.interrupted()
        }
        mentioned.guild.controller.addSingleRoleToMember(mentioned, role).complete(true)
    }

    fun changeColor(role: Role, color: String) {
        val hex: Color
        if (color.contains("#")) {
            hex = Color.decode(color)
        } else {
            hex = Color.decode("#$color")
        }
        role.manager.setColor(hex).complete(true)
    }

    fun makeNewRole(guild: Guild, roleName: String, hoist: Boolean, mentionable: Boolean): Role {
        val rb = guild.controller.createRole()
        rb.setName(roleName)
        rb.setHoisted(hoist)
        rb.setMentionable(mentionable)
        return rb.complete(true)
    }
}

