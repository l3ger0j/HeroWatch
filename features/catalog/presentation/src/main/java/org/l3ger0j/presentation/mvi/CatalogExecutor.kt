package org.l3ger0j.presentation.mvi

import androidx.core.text.isDigitsOnly
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.map
import org.l3ger0j.data.mapper.ConnectivityChecker
import org.l3ger0j.data.mapper.HeroRemoteMediator
import org.l3ger0j.data.mapper.mapToDomain
import org.l3ger0j.data.source.database.AppDatabase
import org.l3ger0j.presentation.mvi.CatalogStore.Action
import org.l3ger0j.presentation.mvi.CatalogStore.Intent
import org.l3ger0j.presentation.mvi.CatalogStore.Label
import org.l3ger0j.presentation.mvi.CatalogStore.Message
import org.l3ger0j.presentation.mvi.CatalogStore.Message.UpdatePagingDataFlow
import org.l3ger0j.presentation.mvi.CatalogStore.State

class CatalogExecutor(
    private val appDatabase: AppDatabase,
    private val connectivityChecker: ConnectivityChecker
) : CoroutineExecutor<Intent, Action, State, Message, Label>() {
    @OptIn(ExperimentalPagingApi::class)
    override fun executeIntent(intent: Intent) {
        when (intent) {
            is Intent.RefreshFilterMap -> {
                forward(Action.InitialLoadPager)
            }

            is Intent.FindTextPaging -> {
                val flowHeroPager =
                    Pager(
                        config = PagingConfig(
                            pageSize = 50,
                            enablePlaceholders = false
                        ),
                        pagingSourceFactory = {
                            if (intent.textToSearch.isDigitsOnly()) {
                                val convStr = intent.textToSearch.replace("^0+(?!$)".toRegex(), "")
                                appDatabase.heroesFTSDAO().heroesLikeId(convStr)
                            } else {
                                appDatabase.heroesFTSDAO().heroesLikeName(intent.textToSearch)
                            }
                        },
                        remoteMediator = HeroRemoteMediator(
                            appDatabase = appDatabase,
                            connectivity = connectivityChecker
                        )
                    ).flow.map { value -> value.map { entityModel -> entityModel.mapToDomain() } }
                dispatch(UpdatePagingDataFlow(flowHeroPager))
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun executeAction(action: Action) {
        when (action) {
            is Action.InitialLoadPager -> {
                val flowHeroPager =
                    Pager(
                        config = PagingConfig(
                            pageSize = 50,
                            enablePlaceholders = false
                        ),
                        pagingSourceFactory = { appDatabase.heroes().all() },
                        remoteMediator = HeroRemoteMediator(
                            appDatabase = appDatabase,
                            connectivity = connectivityChecker
                        )
                    ).flow.map { value -> value.map { entityModel -> entityModel.mapToDomain() } }
                dispatch(UpdatePagingDataFlow(flowHeroPager))
            }
        }
    }
}