package maxdistructo.discord.core

import maxdistructo.discord.core.jda.message.Messages
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.File
import java.io.FileWriter
import java.io.IOException

object JSONUtils {
    /**
     *   Converts the inputed {@link org.json.JSONArray} to an Array of Strings
     *   @param array The JSON array to convert to String
     *   @return The string array or null if the convert is impossible or fails
     */
    fun toStringArray(array: JSONArray?): Array<String?>? {
        if (array == null)
            return null
        val outArray = arrayListOf<String?>()
        array.toList().stream().forEach { e -> outArray.add(e as String) }
        return outArray.toTypedArray()
    }
    /**
     *   Converts the inputed {@link org.json.JSONArray} to an List of Strings
     *   @param array The JSON array to convert to String
     *   @return The list or null if the convert is impossible or fails
     */
    fun toStringList(array: JSONArray?): List<String>? {
        if (array == null)
            return null
        val outList = mutableListOf<String>()
        array.toList().stream().forEach { e -> outList.add(e as String) }
        return outList
    }
    /**
     *   Reads the JSON from the specified file
     *   @param fileName The path to the file in relation to the location of the running directory
     *   @return The JSON object from the file.
     */

    fun readJSONFromFile(fileName: String): JSONObject {
        val file = File(Constants.s + fileName)
        if(!file.exists()){
            createNewJSONFile(Constants.s + fileName)
        }

        val url = file.toURI().toURL()
        var tokener: JSONTokener? = null
        try {
            tokener = JSONTokener(url.openStream())
        } catch (e: IOException) {
            Messages.throwError(e)
            e.printStackTrace()
        }

        return if (tokener != null) {
            JSONObject(tokener)
        } else {
            throw NullPointerException()
        }
    }

    /**
     * Writes the provided JSONObject to file
     * @param path The relative location to the running directory
     * @param jsonObject The object to write to File
     */

    fun writeJSONToFile(path: String, jsonObject: JSONObject) {
        val file = File(Constants.s + path)
        try {
            file.createNewFile()
        } catch (e: IOException) {
            Messages.throwError(e)
        }

        try {
            FileWriter(Constants.s + path).use { fileWriter ->
                fileWriter.write(jsonObject.toString())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    /**
     * Creates a new file with an empty JSONObject in it
     * @param path The relative location to the running directory
     */
    fun createNewJSONFile(path : String){
        val file = File(Constants.s + path)
        try {
            file.createNewFile()
        }
        catch(e : IOException){
            println("Attempted to create file: $path")
            println(e.stackTrace)
        }
        writeJSONToFile(path, JSONObject())
    }

    /**
     * Shuffles the provided JSONArray
     * @param jsonArray The array to shuffle
     * @return The shuffled array
     */

    fun shuffleJSONArray(jsonArray: JSONArray): JSONArray {
        val list: MutableList<String> = mutableListOf()
        list.addAll(toStringList(jsonArray) as Collection<String>)
        list.shuffle()
        return toJSONArray(list)
    }

    /**
     * Converts a Collection into a JSONArray. Primarily used with lists
     * @param list The collection to convert
     * @return JSONArray of the collection
     */
    fun toJSONArray(list: Collection<String>): JSONArray{
        val jsonArray = JSONArray()
        for(value in list){
            jsonArray.put(value)
        }
        return jsonArray
    }
}