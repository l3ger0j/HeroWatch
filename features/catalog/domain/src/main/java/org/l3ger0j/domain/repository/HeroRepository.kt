package org.l3ger0j.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.l3ger0j.domain.model.Hero

interface HeroRepository {
    suspend fun preloadAppDB()
    suspend fun orderHeroPager(orderBy: String): Flow<PagingData<Hero>>
    suspend fun getHeroPager(textToSearch: String): Flow<PagingData<Hero>>
}