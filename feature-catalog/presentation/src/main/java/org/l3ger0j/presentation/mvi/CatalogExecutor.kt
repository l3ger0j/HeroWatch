package org.l3ger0j.presentation.mvi

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import androidx.sqlite.db.SimpleSQLiteQuery
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.map
import org.l3ger0j.data.mapper.ConnectivityChecker
import org.l3ger0j.data.mapper.HeroRemoteMediator
import org.l3ger0j.data.mapper.mapToDomain
import org.l3ger0j.data.source.database.AppDatabase
import org.l3ger0j.domain.usecase.FilterAllCharactersUseCase
import org.l3ger0j.presentation.mvi.CatalogStore.Action
import org.l3ger0j.presentation.mvi.CatalogStore.Intent
import org.l3ger0j.presentation.mvi.CatalogStore.Label
import org.l3ger0j.presentation.mvi.CatalogStore.Message
import org.l3ger0j.presentation.mvi.CatalogStore.Message.UpdatePagingDataFlow
import org.l3ger0j.presentation.mvi.CatalogStore.State

class CatalogExecutor(
    private val filterAllCharactersUseCase: FilterAllCharactersUseCase,
    private val appDatabase: AppDatabase,
    private val connectivityChecker: ConnectivityChecker
) : CoroutineExecutor<Intent, Action, State, Message, Label>() {
    override fun executeIntent(intent: Intent) {
        when (intent) {
            is Intent.RefreshFilterMap -> {
                dispatch(Message.UpdateFilterMap(intent.filter))
                forward(Action.SendPagingDataFlow)
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun executeAction(action: Action) {
        when (action) {
            is Action.SendPagingDataFlow -> {
                val filterMap = state().filterMap
                val flowHeroPager = if (filterMap.isEmpty()) {
                    Pager(
                        config = PagingConfig(
                            pageSize = 50,
                            enablePlaceholders = false
                        ),
                        pagingSourceFactory = { appDatabase.heroes().all() },
                        remoteMediator = HeroRemoteMediator(
                            hashMapOf(),
                            filterAllCharactersUseCase,
                            appDatabase,
                            connectivityChecker
                        )
                    ).flow.map { value -> value.map { entityModel -> entityModel.mapToDomain() } }
                } else {
                    val clauses = mutableListOf<String>()
                    val args = mutableListOf<Any>()

                    filterMap.forEach { (column, value) ->
                        if (value.isNotBlank()) {
                            clauses += "$column LIKE ?"
                            args += "${value.trim()}%"
                        }
                    }
                    val where =
                        if (clauses.isEmpty()) "" else "WHERE " + clauses.joinToString(" AND ")
                    val sql = "SELECT * FROM heroes $where ORDER BY id ASC"
                    val query = SimpleSQLiteQuery(sql, args.toTypedArray())

                    Pager(
                        config = PagingConfig(
                            pageSize = 50,
                            enablePlaceholders = false
                        ),
                        pagingSourceFactory = { appDatabase.heroes().filtered(query) },
                        remoteMediator = HeroRemoteMediator(
                            filterMap,
                            filterAllCharactersUseCase,
                            appDatabase,
                            connectivityChecker
                        )
                    ).flow.map { value -> value.map { entityModel -> entityModel.mapToDomain() } }
                }

                dispatch(UpdatePagingDataFlow(flowHeroPager))
            }
        }
    }
}