package org.l3ger0j.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Hero(
    val id: Int = 0,
    val name: String = "",
    val spriteBackDefault: String = "",
    val spriteFrontDefault: String = ""
)