package org.l3ger0j.data.mapper

import org.l3ger0j.data.source.database.model.HeroesEntityModel
import org.l3ger0j.domain.model.Hero

fun HeroesEntityModel.mapToDomain(): Hero {
    return Hero(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        type = this.type,
        gender = this.gender,
        origin = this.heroOrigin,
        location = this.heroLocation,
        image = this.image,
        episode = this.episode,
        url = this.url,
        created = this.created
    )
}

fun List<HeroesEntityModel>.mapToDomain(): List<Hero> {
    return map {
        Hero(
            id = it.id,
            name = it.name,
            status = it.status,
            species = it.species,
            type = it.type,
            gender = it.gender,
            origin = it.heroOrigin,
            location = it.heroLocation,
            image = it.image,
            episode = it.episode,
            url = it.url,
            created = it.created
        )
    }
}

fun List<Hero>.mapToEntity(): List<HeroesEntityModel> {
    return map { result ->
        HeroesEntityModel(
            id = result.id,
            name = result.name,
            status = result.status,
            species = result.species,
            type = result.type,
            gender = result.gender,
            heroOrigin = result.origin,
            heroLocation = result.location,
            image = result.image,
            episode = result.episode,
            url = result.url,
            created = result.created
        )
    }
}