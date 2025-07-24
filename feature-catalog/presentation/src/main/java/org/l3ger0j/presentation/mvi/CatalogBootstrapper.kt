package org.l3ger0j.presentation.mvi

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.l3ger0j.data.mapper.ConnectivityChecker
import org.l3ger0j.data.mapper.HeroRemoteMediator
import org.l3ger0j.data.mapper.mapToDomain
import org.l3ger0j.data.source.database.AppDatabase
import org.l3ger0j.domain.usecase.FilterAllCharactersUseCase
import org.l3ger0j.presentation.mvi.CatalogStore.Action

class CatalogBootstrapper : CoroutineBootstrapper<Action>() {
    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    override fun invoke() {
        dispatch(Action.SendPagingDataFlow)
    }
}