package maxdistructo.discord.core

import org.json.JSONArray
import org.json.JSONObject
import java.time.Instant
import java.util.*

object Blacklist {

    private fun load(): Array<Long>{
        val json = JSONUtils.readJSONFromFile("/config/blacklist")
        val tempBan = json.getJSONObject("temp_ban")
        val permBan = json.getJSONArray("perm_ban")
        val ret : MutableList<Long> = mutableListOf()

        for(user in tempBan.keys()){
            val ban_time = tempBan.getJSONObject(user).getLong("ban_time")
            val unban_time = tempBan.getJSONObject(user).getLong("unban_time")
            val now = Instant.now().toEpochMilli()
            if(now < unban_time){
                ret + Utils.convertToLong(user)
            }
        }
        for (id in permBan){
            ret + id
        }

        return ret.toTypedArray()
    }
    fun checkId(id: Long): Boolean {
        val blacklist = load()
        return blacklist.contains(id)
       //TimeUtil.getTimeCreated(id)
    }

    /**
     * @funciton getBanLeft
     * @in id: Long - Id of the user who we are looking up a ban duration for
     * @out Long - Time in millis till the ban expires
     */
    fun getBanLeft(id: Long): Long{
        val json = JSONUtils.readJSONFromFile("/config/blacklist")
        val tempBan = json.getJSONObject("temp_ban")
        val permBan = json.getJSONArray("perm_ban")

        for(user in tempBan.keys()){
            if(Utils.convertToLong(user) == id){
                return tempBan.getJSONObject(user).getLong("unban_time") - Instant.now().toEpochMilli()
            }
        }

        return -1
    }

    fun unbanUser(id: Long){
        val json = JSONUtils.readJSONFromFile("/config/blacklist")
        val perm_ban = json.getJSONArray("perm_ban")
        val tempBan = json.getJSONObject("temp_ban")
        val permOut = JSONArray()
        try{
            tempBan.remove("" + id)
        }
        catch(e: Exception){}
        for(permid in perm_ban){
            if(permid != id){
                permOut.put(id)
            }
        }
        json.remove("perm_ban")
        json.remove("temp_ban")
        json.append("perm_ban", permOut)
        json.append("temp_ban", tempBan)
        JSONUtils.writeJSONToFile("/config/blacklist", json)
    }

    //YOU HAVE BEEN PERM BANNED!
    fun banUser(id: Long){
        val json = JSONUtils.readJSONFromFile("/config/blacklist")
        val perm_ban = json.getJSONArray("perm_ban")
        json.remove("perm_ban")
        perm_ban.put(id)
        json.put("perm_ban", perm_ban)
        JSONUtils.writeJSONToFile("/config/blacklist", json)
    }

    //You have been banned, but only temporarily
    fun banUser(id: Long, duration: Long){
        val json = JSONUtils.readJSONFromFile("/config/blacklist")
        val temp_ban = json.getJSONObject("temp_ban")
        val now = Instant.now().toEpochMilli()
        temp_ban.put("" + id, JSONObject().put("ban_time", now).put("unban_time", now + duration))
        JSONUtils.writeJSONToFile("/config/blacklist", json)
    }
    //Verification that the user can ban is done in the command definition
    fun banUser(id: Long, days: Int, hours: Int, minutes: Int, seconds: Int){
        var duration: Long = 0
        duration += days * (60 * 60 * 24)
        duration += hours * (60 * 60)
        duration += minutes * (60)
        duration += seconds
        banUser(id, duration)
    }

    fun banUser(id: Long, date: Calendar){
        banUser(id, date.toInstant().toEpochMilli() - Instant.now().toEpochMilli())
    }
}