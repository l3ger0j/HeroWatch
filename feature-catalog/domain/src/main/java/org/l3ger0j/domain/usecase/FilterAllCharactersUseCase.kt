package org.l3ger0j.domain.usecase

import org.l3ger0j.domain.model.ServerResponse
import org.l3ger0j.domain.repository.HeroRepository

class FilterAllCharactersUseCase(
    private val heroRepository: HeroRepository
) {
    suspend fun execute(link: String, filterMap: HashMap<String, String>): ServerResponse {
        return heroRepository.getAllCharacters(link, filterMap)
    }
}