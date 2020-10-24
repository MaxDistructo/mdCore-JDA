package maxdistructo.discord.core.jda_utils

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.Blacklist
import maxdistructo.discord.core.jda.JDAUtils
import java.util.*

object Blacklisting {
    class CommandBan : Command(){
        init{
            this.help = "ban <@User> <Time(#d,#h,#m,#s)/Date(mm,dd,yyyy)> | Bans a user for the specified amount of time or until the specified date. If no time is specified, they are perm banned unless pardoned."
            this.name = "ban"
            this.guildOnly = false
            this.ownerCommand = true
        }
        override fun execute(event: CommandEvent?) {
            //Prepare Interpretation of Ban Command
            val content = event!!.message.contentRaw
            val mentioned = JDAUtils.getMentionedUser(event.message)
            //command, mention, interpret
            val split = content.split(" ")
            if(split[2].contains("d") || split[2].contains("h") || split[2].contains("m") || split[2].contains("s")){
                val days = Utils.convertToInt(split[2].split("d")[0].replace(",",""))
                val hours = Utils.convertToInt(split[2].split("d")[1].split("h")[0].replace(",",""))
                val minutes = Utils.convertToInt(split[2].split("d")[1].split("h")[1].split("m")[0].replace(",",""))
                val seconds = Utils.convertToInt(split[2].split("d")[1].split("h")[1].split("m")[1].replace("s", "").replace(",",""))
                Blacklist.banUser(mentioned!!.idLong, days, hours, minutes, seconds)
            }
            else{
                val month = Utils.convertToInt(split[2].split(",")[0])
                val day = Utils.convertToInt(split[2].split(",")[1])
                val year = Utils.convertToInt(split[2].split(",")[2])
                Blacklist.banUser(mentioned!!.idLong, GregorianCalendar(year, month - 1, day))
            }
        }

    }
    class CommandUnban : Command(){
        init{
            this.help = "unban <@User> | Unbans a user"
            this.name = "unban"
            this.guildOnly = false
            this.ownerCommand = true
        }
        override fun execute(event: CommandEvent?) {
            Blacklist.unbanUser(JDAUtils.getMentionedUser(event!!.message)!!.idLong)
        }
    }
}