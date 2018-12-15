package maxdistructo.discord.core.jda

import maxdistructo.discord.core.jda.message.Messages
import net.dv8tion.jda.core.entities.*
import java.awt.Color

/**
 * @object Roles
 * @description This class contains methods to get and modify Discord roles.
 * @author MaxDistructo
 */

object Roles {

    /**
     * Function getRole
     * Attempts to get a role based on the role name provided
     * @param message The message from the guild you wish to get a role from
     * @param role The name of the role to get. Is not case sensitive
     */
    fun getRole(message: Message, role: String): Role? {
        return getRole(message.guild, role)
    }

    /**
     * Function getRole
     * Attempts to get a role based on the role name provided
     * @param guild The guild you wish to get a role from
     * @param role The name of the role to get. Is not case sensitive
     */
    fun getRole(guild : Guild, role : String) : Role?{
        val roles = guild.getRolesByName(role, true)
        return if (roles.size != 0) {
            roles[0]
        } else {
            null
        }
    }

    /**
     * Function copyRolePerms
     * Gets the permissions from the first role and puts them on that of the second. This is a very powerful function so use it wisely.
     * @param copy The role to get permissions from
     * @param paste The role to paste the permissions on
     */

    fun copyRolePerms(copy: Role, paste: Role) {
        val permissions = copy.permissions
        paste.permissions.addAll(permissions)
        paste.manager.givePermissions(permissions).complete(true)
    }

    /**
     * Function applyRole
     * Applies a role to a Member.
     * @param message A message from the guild you wish to give a role to a member
     * @param mentioned The member you wish to get the role.
     * @param role The name of the role you wish to apply.
     */

    fun applyRole(message: Message, mentioned: Member, role: String) {
        applyRole(message.guild, mentioned.user, role)
    }

    fun applyRole(message: Message, mentioned: Member, roleL: Long) {
        val role = message.guild.getRoleById(roleL)
        if (role == null) {
            Messages.sendMessage(message.channel, "The role $roleL was not found.")
            Thread.interrupted()
        }
        mentioned.guild.controller.addSingleRoleToMember(mentioned, role).complete(true)
    }

    fun applyRole(guild : Guild, user : User, roleName : String){
        val roleList = guild.getRolesByName(roleName, true)
        if (roleList.isEmpty()) {
            Thread.interrupted()
        }
        if (!roleList.isEmpty()) {
            guild.controller.addSingleRoleToMember(guild.getMember(user), roleList[0]).complete(true)
        }
    }

    fun applyRole(member : Member, roleName : String){
        applyRole(member.guild, member.user, roleName)
    }

    /**
     * Function changeColor
     * Will change the color of a role to the one inputted as a String.
     */

    fun changeColor(role: Role, color: Color) {
        role.manager.setColor(color).complete(true)
    }

    fun changeColor(guild : Guild, roleName : String, color : String){
        val hex: Color = if (color.contains("#")) {
            Color.decode(color)
        } else {
            Color.decode("#$color")
        }
        changeColor(getRole(guild, roleName)!!, hex)
    }

    /**
     * Function makeNewRole
     * Makes a new role in the provided guild.
     */

    fun makeNewRole(guild: Guild, roleName: String, hoist: Boolean, mentionable: Boolean): Role {
        val rb = guild.controller.createRole()
        rb.setName(roleName)
        rb.setHoisted(hoist)
        rb.setMentionable(mentionable)
        return rb.complete(true)
    }
}

