package maxdistructo.discord.core.jda_utils

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import com.jagrosh.jdautilities.command.CommandListener
import maxdistructo.discord.core.Blacklist
import maxdistructo.discord.core.jda.Client
import java.util.concurrent.TimeUnit

class BlacklistCommandListener(val ownerId: Long) : CommandListener {
    override fun onCommand(event: CommandEvent?, command: Command?) {
        if(Blacklist.checkId(event!!.message.author.idLong)){
            event.reply("You have been banned from running commands on this bot.")
            val timeFail = Blacklist.getBanLeft(event.message.author.idLong)
            if(timeFail != -1L){
                event.reply("You will be unbanned in ${calculateTime(timeFail)} milliseconds.")
            }
            else{
                event.reply("You are currently permbanned. Please appeal with ${Client.client!!.getUserById(ownerId)!!.asMention}")
            }
        }
        else {
            super.onCommand(event, command)
        }
    }
    companion object{
        //Converted from stackoverflow java code. Output was also tweaked and a return statement was used instead of a println.
        private fun calculateTime(seconds: Long): String {
            val day = TimeUnit.SECONDS.toDays(seconds).toInt()
            val hours = TimeUnit.SECONDS.toHours(seconds) - day * 24
            val minute = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * 60
            val second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toMinutes(seconds) * 60
            return "$day day(s) $hours hour(s) $minute minute(s) and $second second(s)"
        }
    }
}
