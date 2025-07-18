package org.l3ger0j.data.source.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.l3ger0j.domain.model.HeroLocation
import org.l3ger0j.domain.model.HeroOrigin

@Entity(tableName = "heroes")
data class HeroesEntityModel(
    @PrimaryKey
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = "",
    val heroOrigin: HeroOrigin = HeroOrigin(),
    val heroLocation: HeroLocation = HeroLocation(),
    val image: String = "",
    val episode: List<String> = emptyList(),
    val url: String = "",
    val created: String = ""
)