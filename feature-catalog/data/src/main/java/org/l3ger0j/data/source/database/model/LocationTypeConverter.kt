package org.l3ger0j.data.source.database.model

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import org.l3ger0j.domain.model.HeroLocation

class LocationTypeConverter {
    @TypeConverter
    fun convertLocationToJSONString(heroLocationClazz: HeroLocation): String = Json.encodeToString(heroLocationClazz)
    @TypeConverter
    fun convertJSONStringToLocation(locationString: String): HeroLocation = Json.decodeFromString(locationString)
}