package org.l3ger0j.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.l3ger0j.domain.model.Hero
import org.l3ger0j.domain.repository.HeroRepository

class OrderByPagerUseCase(
    private val heroRepository: HeroRepository
) {
    suspend fun execute(orderBy: String): Flow<PagingData<Hero>> {
        return heroRepository.orderHeroPager(orderBy)
    }
}