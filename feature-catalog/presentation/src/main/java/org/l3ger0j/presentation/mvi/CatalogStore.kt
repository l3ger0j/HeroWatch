package org.l3ger0j.presentation.mvi

import androidx.paging.PagingData
import com.arkivanov.mvikotlin.core.store.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.serialization.Serializable
import org.l3ger0j.domain.model.Hero
import org.l3ger0j.presentation.mvi.CatalogStore.Intent
import org.l3ger0j.presentation.mvi.CatalogStore.Label
import org.l3ger0j.presentation.mvi.CatalogStore.State

interface CatalogStore : Store<Intent, State, Label> {
    data class State(
        val filterMap: HashMap<String, String> = hashMapOf(),
        val flowPagingData: Flow<PagingData<Hero>> = emptyFlow()
    )

    sealed interface Intent {
        data class RefreshFilterMap(val filter: HashMap<String, String>) : Intent
    }

    sealed interface Message {
        data class UpdatePagingDataFlow(val flowPager: Flow<PagingData<Hero>>) : Message
        data class UpdateFilterMap(val filter: HashMap<String, String>) : Message
    }

    sealed interface Label

    sealed interface Action {
        data object SendPagingDataFlow : Action
    }
}