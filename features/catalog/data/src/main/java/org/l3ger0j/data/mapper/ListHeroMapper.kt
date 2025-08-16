package org.l3ger0j.data.mapper

import org.l3ger0j.data.source.database.model.HeroesEntityModel
import org.l3ger0j.domain.model.Hero

fun HeroesEntityModel.mapToDomain(): Hero {
    return Hero(
        id = this.id,
        name = this.name,
        spriteFrontDefault = this.spriteFrontDefault
    )
}

fun List<HeroesEntityModel>.mapToDomain(): List<Hero> {
    return map {
        Hero(
            id = it.id,
            name = it.name,
            spriteFrontDefault = it.spriteFrontDefault
        )
    }
}

fun List<Hero>.mapToEntity(): List<HeroesEntityModel> {
    return map { result ->
        HeroesEntityModel(
            id = result.id,
            name = result.name,
            spriteFrontDefault = result.spriteFrontDefault
        )
    }
}