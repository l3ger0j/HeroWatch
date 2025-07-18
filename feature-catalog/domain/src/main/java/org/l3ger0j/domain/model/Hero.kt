package org.l3ger0j.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Hero(
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = "",
    val origin: HeroOrigin = HeroOrigin(),
    val location: HeroLocation = HeroLocation(),
    val image: String = "",
    val episode: List<String> = emptyList(),
    val url: String = "",
    val created: String = ""
)