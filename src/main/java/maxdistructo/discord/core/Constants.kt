package maxdistructo.discord.core

import java.nio.file.Path
import java.nio.file.Paths

object Constants {
    val currentRelativePath: Path = Paths.get("")
    val s = currentRelativePath.toAbsolutePath().toString()
}