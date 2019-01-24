package maxdistructo.discord.core.jda.impl

import maxdistructo.discord.core.jda.obj.IPerms
import maxdistructo.discord.core.jda.Utils
import net.dv8tion.jda.core.entities.Guild
import net.dv8tion.jda.core.entities.Member
import net.dv8tion.jda.core.entities.User
import net.dv8tion.jda.core.entities.Message
import org.json.JSONObject
import org.json.JSONArray

/**
 * @class Perms
 * A simple permissions class. Does not use IPerms due to how JSONArrays hold values
 * @param fileSuffix The suffix of the file name. Ex: _perms.json is the suffix of GuildID_perms.json.
 * @param guild The guild this permissions object is tied to.
 */

class Perms(fileSuffix : String, guild : Guild){
    val jsonFile : JSONObject
    val guildId : Long
    
    constructor(guild : Guild){
        jsonFile = Utils.readJSONFromFile("/config/" + guild.idLong + "_perms.json")
        guildId = guild.idLong
    }
    init{
        jsonFile = Utils.readJSONFromFile("/config/" + guild.idLong + fileSuffix)
        guildId = guild.idLong
    }

    val mods = Utils.toStringList(jsonFile.getJSONArray("mods"))
    val admins = Utils.toStringList(jsonFile.getJSONArray("admins"))
    val owner = guild.getOwner.user.idLong

    //Default Permission Check Function
    fun checkMod(id : Long) : Boolean{
        return mods.contains("" + id) || admins.contains("" + id) || id == owner
    }
    fun checkAdmin(id : Long) : Boolean{
        return admins.contains("" + id) || id == owner
    }
    fun checkOwner(id : Long) : Boolean{
        return id == owner
    }

    //Variants of Default Permission Check Function
    fun checkMod(user : User) : Boolean{
        return checkMod(user.idLong)
    }
    fun checkMod(member : Member) : Boolean{
        return checkMod(member.user.idLong)
    }
    fun checkMod(message : Message) : Boolean{
        return checkMod(message.author.idLong)
    }
    fun checkMod(idString : String) : Boolean{
        return checkMod(Utils.convertToLong(idString))
    }
    fun checkAdmin(user : User) : Boolean{
        return checkAdmin(user.idLong)
    }
    fun checkAdmin(member : Member) : Boolean{
        return checkAdmin(member.user.idLong)
    }
    fun checkAdmin(message : Message) : Boolean{
        return checkAdmin(message.author.idLong)
    }
    fun checkAdmin(idString : String) : Boolean{
        return checkAdmin(Utils.convertToLong(idString))
    }
    fun checkOwner(user : User) : Boolean{
        return checkOwner(user.idLong)
    }
    fun checkOwner(member : Member) : Boolean{
        return checkOwner(member.user.idLong)
    }
    fun checkOwner(message : Message) : Boolean{
        return checkOwner(message.author.idLong)
    }
    fun checkOwner(idString : String) : Boolean{
        return checkOwner(Utils.convertToLong(idString))
    }

}