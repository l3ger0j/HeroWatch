package org.l3ger0j.data.mapper

import org.l3ger0j.data.source.database.model.HeroResponseEntityModel
import org.l3ger0j.domain.model.HeroResponse

fun HeroResponseEntityModel.mapToDomain(): HeroResponse {
    return HeroResponse(
        next = this.next,
        prev = this.prev
    )
}

fun HeroResponse.mapToEntity(): HeroResponseEntityModel {
    return HeroResponseEntityModel(
        next = this.next,
        prev = this.prev
    )
}