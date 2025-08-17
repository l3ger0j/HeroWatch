package org.l3ger0j.domain.usecase

import org.l3ger0j.domain.repository.HeroRepository

class PreloadAppDBUseCase(
    private val heroRepository: HeroRepository
) {
    suspend fun execute() {
        heroRepository.preloadAppDB()
    }
}