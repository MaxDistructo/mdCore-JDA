package maxdistructo.discord.core.jda

import maxdistructo.discord.core.jda.message.Messages
import net.dv8tion.jda.core.entities.Guild
import org.json.JSONObject
import org.json.JSONTokener

import java.io.File
import java.io.IOException
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
        val root = Utils.readJSONFromFile("/config/config.txt")
        return root.getString("Token")

    }

    fun readPrefix(): String {
        val root = Utils.readJSONFromFile("/config/config.txt")
        return root.getString("Prefix")
    }

    fun readServerModConfig(guild: Guild): List<Long> {
        val root = Utils.readJSONFromFile("/config/guild/" + guild.idLong + ".txt")
        val array = root.getJSONArray("Moderators")
        val longArray = LongArray(array.length())
        var i = 0
        while (i < longArray.size) {
            longArray[i] = array.getLong(i)
            i++
        }
        return longArray.toList()
    }

    fun readServerAdminConfig(guild: Guild): List<Long> {
        val root = Utils.readJSONFromFile("/config/guild/" + guild.idLong + ".txt")
        val array = root.getJSONArray("Admins")
        val longArray = LongArray(array.length())
        var i = 0
        while (i < array.length()) {
            longArray[i] = array.getLong(i)
            i++
        }
        return longArray.toList()
    }

    fun readServerGamesConfig(guild: Guild): List<String?> {
        val root = Utils.readJSONFromFile("/config/guild/" + guild.idLong + ".txt")
        val array = root.getJSONArray("GameChannels")
        var longArray = listOf<String>()
        var i = 0
        while (i < array.length()) {
            longArray += array.getString(i)
            i++
        }
        return longArray
    }

    @Deprecated("", ReplaceWith("Utils.readJSONFromFile(\"/config/guild/ + guild.idLong + \".txt\"", "maxdistructo.discord.core.jda.Utils" ))
    fun readServerConfig(guild: Guild): JSONObject {
        return Utils.readJSONFromFile("/config/guild/" + guild.idLong + ".txt")
    }

    @Deprecated("", ReplaceWith("Utils.convertToLong(o)", "maxdistructo.discord.core.jda.Utils.convertToLong", "maxdistructo.discord.core.jda.Utils")) //Use Utils.convertToLong instead. This is here for legacy support.
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
        return Utils.readJSONFromFile("/config/config.txt")
    }
}


