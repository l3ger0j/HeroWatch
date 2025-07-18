package org.l3ger0j.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class HeroOrigin(
    val name: String = "",
    val url: String = ""
)
