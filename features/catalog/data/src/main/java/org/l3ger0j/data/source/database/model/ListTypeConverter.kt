package org.l3ger0j.data.source.database.model

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class ListTypeConverter {
    @TypeConverter
    fun convertEpisodeListToJSONString(episodeList: List<String>): String = Json.encodeToString(episodeList)
    @TypeConverter
    fun convertJSONStringToEpisodeList(episodeString: String): List<String> = Json.decodeFromString(episodeString)
}