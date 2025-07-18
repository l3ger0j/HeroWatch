package org.l3ger0j.presentation.mvi

import androidx.paging.PagingData
import com.arkivanov.mvikotlin.core.store.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.l3ger0j.domain.model.Hero
import org.l3ger0j.presentation.mvi.CatalogStore.Intent
import org.l3ger0j.presentation.mvi.CatalogStore.Label
import org.l3ger0j.presentation.mvi.CatalogStore.State

interface CatalogStore : Store<Intent, State, Label> {
    data class State(
        val flowPagingData: Flow<PagingData<Hero>> = emptyFlow()
    )

    sealed interface Intent {
        data class RefreshPagingDataFlow(val filter: HashMap<String, String>) : Intent
    }

    sealed interface Message {
        data class UpdatePagingDataFlow(val flowPager: Flow<PagingData<Hero>>) : Message
    }

    sealed interface Label

    sealed interface Action {
        data class SendPagingDataFlow(val flowPager: Flow<PagingData<Hero>>) : Action
    }
}