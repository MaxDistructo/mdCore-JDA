package maxdistructo.discord.core.jda.priv

import maxdistructo.discord.core.jda.Client
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder

internal object Client{
  fun createClient(token : String) : JDA?{
        lateinit var client : JDA
        val clientBuilder = JDABuilder(AccountType.BOT) // Creates the ClientBuilder instance
        clientBuilder.setToken(token) // A to the builder
        try {
            client = clientBuilder.build().awaitReady()
        } catch (e: Exception) {
            e.printStackTrace()
            System.exit(0)
        }
        Client.client = client // This variable is used in Messages and I can not touch it.
        return client
  }

}
