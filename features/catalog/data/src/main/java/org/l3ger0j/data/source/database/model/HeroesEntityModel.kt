package org.l3ger0j.data.source.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "heroes")
data class HeroesEntityModel(
    @PrimaryKey
    val id: Int = 0,
    val name: String = "",
    val spriteFrontDefault: String = ""
)