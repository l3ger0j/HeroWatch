package org.l3ger0j.data.source.database.model

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import org.l3ger0j.domain.model.HeroOrigin

class OriginTypeConverter {
    @TypeConverter
    fun convertOriginToJSONString(heroOriginClazz: HeroOrigin): String = Json.encodeToString(heroOriginClazz)
    @TypeConverter
    fun convertJSONStringToOrigin(originString: String): HeroOrigin = Json.decodeFromString(originString)
}