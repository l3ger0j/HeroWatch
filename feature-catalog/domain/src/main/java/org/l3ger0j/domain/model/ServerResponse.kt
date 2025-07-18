package org.l3ger0j.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse(
    val info: HeroResponse = HeroResponse(),
    val results: List<Hero> = emptyList()
)