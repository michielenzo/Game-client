package idema.michiel.utilities

import com.google.gson.JsonObject
import com.google.gson.JsonParser

interface IJSON{
    fun convertToObject(string: String): JsonObject
    fun convertToString(json: JsonObject): String
}


object JSON: IJSON {

    override fun convertToString(json: JsonObject): String {
       return json.toString()
    }

    override fun convertToObject(string: String): JsonObject {
        return JsonParser().parse(string).asJsonObject
    }

}