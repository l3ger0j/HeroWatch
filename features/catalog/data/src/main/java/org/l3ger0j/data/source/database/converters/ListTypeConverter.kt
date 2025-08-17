package org.l3ger0j.data.source.database.converters

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class ListTypeConverter {
    @TypeConverter
    fun convertTypesListToJSONString(typesList: List<String>): String =
        Json.encodeToString(typesList)

    @TypeConverter
    fun convertJSONStringToTypesList(typeString: String): List<String> =
        Json.decodeFromString(typeString)
}