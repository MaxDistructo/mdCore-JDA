package maxdistructo.discord.core

object Utils {
    @Deprecated("Moved to Constants", ReplaceWith("Constants.s", "maxdistructo.discord.core.Constants"))
    val s = Constants.s
    /**
     *   Creates a new String using the provided Array and a position to start at. This start position is based on the number in the array.
     *   Ex. To cut out the first value in an array and make it into a string, input 1 for startAt
     *   @param input The array to draw from
     *   @param startAt The value to start from
     *   @return A string built off of all the values the array after the specified value.
     */
    fun makeNewString(input: List<Any>, startAt: Int): String {
        val stringBuilder = StringBuilder()
        input.asReversed().dropLast(startAt - 1).asReversed().forEach { e -> stringBuilder.append(e) }
        return stringBuilder.toString()
    }

    /**
     *   Converts the input to a Long value. Returns null if the convert fails.
     *   @param o The object to try and convert to Long
     *   @return The long value of the input or null if it is unable to be converted
     */

    fun convertToLong(o: Any): Long? {
        return try {
            java.lang.Long.valueOf(o.toString())
        } catch (e: Exception) {
            null
        }
    }

    /**
     *   Converts the input to an Int value. Returns null if the convert fails or is impossible.
     *   @param in The object to convert
     *   @return The converted object or null
     */

    fun convertToInt(`in`: Any): Int {
        return Integer.valueOf(`in`.toString())
    }

    /**
     *   Similar to #makeNewString but puts new lines between the values instead of spaces
     *   @param input The array to convert
     *   @param startAt The spot in the array to start reading at
     *   @return The String with new lines
     */

    fun makeNewLineString(input: List<String?>, startAt: Int): String {
        val stringBuilder = StringBuilder()
        input.asReversed().dropLast(startAt - 1).asReversed().forEach { e -> stringBuilder.append(e) }
        return stringBuilder.toString()
    }

}
