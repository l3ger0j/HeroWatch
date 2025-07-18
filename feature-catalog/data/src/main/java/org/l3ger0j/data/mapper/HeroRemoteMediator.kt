package org.l3ger0j.data.mapper

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import org.l3ger0j.data.repository.HeroRepositoryImpl.Companion.REPO_LINK
import org.l3ger0j.data.source.database.AppDatabase
import org.l3ger0j.data.source.database.model.HeroResponseEntityModel
import org.l3ger0j.data.source.database.model.HeroesEntityModel
import org.l3ger0j.domain.model.Hero
import org.l3ger0j.domain.usecase.FilterAllCharactersUseCase

@OptIn(ExperimentalPagingApi::class)
class HeroRemoteMediator(
    private val filter: HashMap<String, String>,
    private val allHero: FilterAllCharactersUseCase,
    private val appDatabase: AppDatabase,
    private val connectivity: ConnectivityChecker
) : RemoteMediator<Int, HeroesEntityModel>() {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HeroesEntityModel>
    ): MediatorResult {
        if (!connectivity.isOnline()) {
            return MediatorResult.Success(endOfPaginationReached = true)
        }

        val loadKey = when (loadType) {
            LoadType.REFRESH -> {
                val pos = state.anchorPosition ?: 0
                val id = state.closestItemToPosition(pos)?.id ?: 0
                appDatabase.heroResponse().getElementById(id)?.next ?: REPO_LINK
            }

            LoadType.PREPEND -> {
                val id = state.firstItemOrNull()?.id ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                val prev = appDatabase.heroResponse().getElementById(id)?.prev
                if (prev.isNullOrEmpty()) {
                    return MediatorResult.Success(endOfPaginationReached = prev.isNullOrEmpty())
                } else {
                    prev
                }
            }

            LoadType.APPEND -> {
                val id = state.lastItemOrNull()?.id ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                val next = appDatabase.heroResponse().getElementById(id)?.next
                if (next.isNullOrEmpty()) {
                    return MediatorResult.Success(endOfPaginationReached = next.isNullOrEmpty())
                } else {
                    next
                }
            }
        }

        val apiResponse = allHero.execute(loadKey, filter)
        val heroes = apiResponse.results
        val endOfPaginationReached = heroes.isEmpty()

        appDatabase.withTransaction {
            if (loadType == LoadType.REFRESH && heroes.isNotEmpty()) {
                appDatabase.heroes().clearAll()
                appDatabase.heroResponse().clearAll()
            }

            val prev = apiResponse.info.prev
            val next = if (endOfPaginationReached) "" else apiResponse.info.next
            val keys = heroes.map {
                HeroResponseEntityModel(heroId = it.id, next = next, prev = prev)
            }

            appDatabase.heroResponse().insertOrReplaceAll(keys)
            appDatabase.heroes().insertOrReplaceAll(heroes.mapToEntity())
        }

        return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
    }
}