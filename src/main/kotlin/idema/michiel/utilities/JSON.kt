package idema.michiel.utilities

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser

interface IJSON{
    fun convertStrinToGSONObject(string: String): JsonObject
    fun convertGSONObjectToString(json: JsonObject): String
    fun convertDTOObjectToString(dto: DTO): String
}


object JSON: IJSON {

    override fun convertDTOObjectToString(dto: DTO): String {
       return Gson().toJson(dto)
    }

    override fun convertGSONObjectToString(json: JsonObject): String {
       return json.toString()
    }

    override fun convertStrinToGSONObject(string: String): JsonObject {
        return JsonParser().parse(string).asJsonObject
    }

}