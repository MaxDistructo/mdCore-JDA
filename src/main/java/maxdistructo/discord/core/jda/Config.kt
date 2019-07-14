package maxdistructo.discord.core.jda

import maxdistructo.discord.core.JSONUtils
import maxdistructo.discord.core.jda.message.Messages
import net.dv8tion.jda.api.entities.Guild
import org.json.JSONObject

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

/**
 * @object Config
 * @description This class contains methods for getting and writing values to the local config files.
 * @author MaxDistructo
 */

object Config {

    @Deprecated("", ReplaceWith("Integer.valueOf(`in`.toString())"))
    fun converToInt(`in`: Any): Int {
        return Integer.valueOf(`in`.toString())
    }

    fun readToken(): String {
        val root = JSONUtils.readJSONFromFile("/config/config.txt")
        return root.getString("Token")

    }

    fun readPrefix(): String {
        val root = JSONUtils.readJSONFromFile("/config/config.txt")
        return root.getString("Prefix")
    }

    fun readServerModConfig(guild: Guild): List<Long> {
        val root = JSONUtils.readJSONFromFile("/config/guild/" + guild.idLong + ".txt")
        val array = root.getJSONArray("Moderators")
        return array.toList() as List<Long>
    }

    fun readServerAdminConfig(guild: Guild): List<Long> {
        val root = JSONUtils.readJSONFromFile("/config/guild/" + guild.idLong + ".txt")
        val array = root.getJSONArray("Admins")
        return array.toList() as List<Long>
    }

    fun readServerGamesConfig(guild: Guild): List<String?> {
        val root = JSONUtils.readJSONFromFile("/config/guild/" + guild.idLong + ".txt")
        val array = root.getJSONArray("GameChannels")
        return array.toMutableList() as List<String?>
    }

    @Deprecated("", ReplaceWith("JSONUtils.readJSONFromFile(\"/config/guild/ + guild.idLong + \".txt\"", "maxdistructo.discord.core.Utils" ))
    fun readServerConfig(guild: Guild): JSONObject {
        return JSONUtils.readJSONFromFile("/config/guild/" + guild.idLong + ".txt")
    }

    @Deprecated("", ReplaceWith("Utils.convertToLong(o)", "maxdistructo.discord.core.Utils.convertToLong", "maxdistructo.discord.core.Utils")) //Use Utils.convertToLong instead. This is here for legacy support.
    fun convertToLong(o: Any): Long {
        return java.lang.Long.valueOf(o.toString())
    }

    fun readFileAsList(file: File): List<String>? {
        var lines: List<String>? = null
        try {
            lines = Files.readAllLines(Paths.get(file.toURI()))
        } catch (e: Exception) {
            Messages.throwError(e)
        }

        return lines
    }

    fun checkDebug() : Boolean{
        val config = readConfig()
        return config.getBoolean("debug")
    }
    fun readConfig() : JSONObject{
        return JSONUtils.readJSONFromFile("/config/config.txt")
    }
}


