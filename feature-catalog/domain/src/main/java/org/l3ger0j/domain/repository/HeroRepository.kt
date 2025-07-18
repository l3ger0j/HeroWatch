package org.l3ger0j.domain.repository

import org.l3ger0j.domain.model.ServerResponse

interface HeroRepository {
    suspend fun getAllCharacters(link: String, filterMap: HashMap<String, String>): ServerResponse
}