package org.l3ger0j.data.source.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hero_response")
data class HeroResponseEntityModel(
    @PrimaryKey
    val heroId: Int = 0,
    val next: String = "",
    val prev: String = ""
)