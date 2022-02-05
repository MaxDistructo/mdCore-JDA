package maxdistructo.discord.core.testbot

import maxdistructo.discord.core.jda.impl.Bot
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException
import java.util.Scanner

object TestBot {
    //Prevent Token Leaks by taking in the token from command line. *THIS IS NOT A TEMPLATE BOT IN ANY MEANS*
    var token = ""

    @JvmStatic
    fun main(args: Array<String>){
        val scanner = Scanner(System.`in`)
        println("Input your bot token: ")
        token = scanner.nextLine()
        val bot = Bot(token, "!!", "0")
        bot.registerListener(TestListener())
        bot.disableJDAUtils()
        bot.init()
        for(guild in bot.client.guilds) {
            for (channel in guild.channels) {
                if (channel is TextChannel) {
                    try {
                        val latestMessage = channel.retrieveMessageById(channel.latestMessageId).complete()
                        println("${guild.name}, ${channel.name}: ${latestMessage!!.contentDisplay}")
                    } catch (e: InsufficientPermissionException) {
                        println("Cannot read ${guild.name}, ${channel.name}")
                    } catch (e: IllegalStateException) {
                        println("No messages in ${guild.name}, ${channel.name}")
                    }
                } else {
                    println("${guild.name}, ${channel.name}: Non Text Channel")
                }
            }
        }
    }


}