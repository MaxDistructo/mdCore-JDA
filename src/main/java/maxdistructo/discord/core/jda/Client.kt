package maxdistructo.discord.core.jda

import net.dv8tion.jda.core.JDA
import org.slf4j.Logger

/**
*
* This class is used for one purpose and that is to hold variables necessary for the rest of the core to work. This includes the client and logger variables.
*
*/

internal object Client {
    var client: JDA? = null
    var LOGGER : Logger? = null
}
