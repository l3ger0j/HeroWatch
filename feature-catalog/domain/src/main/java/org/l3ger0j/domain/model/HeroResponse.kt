package org.l3ger0j.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class HeroResponse(
    val count: Int = 0,
    val pages: Int = 0,
    val next: String = "",
    val prev: String = ""
)
