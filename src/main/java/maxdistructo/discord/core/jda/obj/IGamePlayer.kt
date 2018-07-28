package maxdistructo.discord.core.jda.obj

import org.json.JSONObject

interface IGamePlayer {
    val id : Long
    val playerInfo : JSONObject
    fun getInfo() : JSONObject{
        return playerInfo
    }
    fun getID() : Long{
        return id
    }
}