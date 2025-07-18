package org.l3ger0j.presentation.mvi

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import androidx.sqlite.db.SimpleSQLiteQuery
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
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
    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    override fun executeIntent(intent: Intent) {
        when (intent) {
            is Intent.RefreshPagingDataFlow -> {
                scope.launch {
                    val clauses = mutableListOf<String>()
                    val args = mutableListOf<Any>()

                    intent.filter.forEach { (column, value) ->
                        if (value.isNotBlank()) {
                            clauses += "$column LIKE ?"
                            args += "%${value.trim()}%"
                        }
                    }
                    val where =
                        if (clauses.isEmpty()) "" else "WHERE " + clauses.joinToString(" AND ")
                    val sql = "SELECT * FROM heroes $where ORDER BY id ASC"
                    val query = SimpleSQLiteQuery(sql, args.toTypedArray())

                    val flowHeroPager = Pager(
                        config = PagingConfig(
                            pageSize = 10,
                            enablePlaceholders = false
                        ),
                        pagingSourceFactory = { appDatabase.heroes().getAllSearchPaging(query) },
                        remoteMediator = HeroRemoteMediator(
                            intent.filter,
                            filterAllCharactersUseCase,
                            appDatabase,
                            connectivityChecker,
                        )
                    ).flow.map { value -> value.map { entityModel -> entityModel.mapToDomain() } }

                    dispatch(UpdatePagingDataFlow(flowHeroPager))
                }
            }
        }
    }

    override fun executeAction(action: Action) {
        when (action) {
            is Action.SendPagingDataFlow -> dispatch(UpdatePagingDataFlow(action.flowPager))
        }
    }
}