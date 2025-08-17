package org.l3ger0j.catalog.presentation.mvi

import androidx.paging.PagingData
import com.arkivanov.mvikotlin.core.store.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import org.l3ger0j.catalog.presentation.mvi.CatalogStore.Intent
import org.l3ger0j.catalog.presentation.mvi.CatalogStore.Label
import org.l3ger0j.catalog.presentation.mvi.CatalogStore.State
import org.l3ger0j.domain.model.Hero

interface CatalogStore : Store<Intent, State, Label> {
    @Serializable
    data class State(
        val textToSearch: String = ""
    )

    sealed interface Intent {
        data object RefreshFilterMap : Intent
        data class OrderTextPaging(val orderBy: String) : Intent
        data class FindTextPaging(val textToSearch: String) : Intent
    }

    sealed interface Message {
        data class UpdateTextToSearch(val textToSearch: String) : Message
    }

    sealed interface Label {
        data class UpdatePagingDataFlow(val flowPager: Flow<PagingData<Hero>>) : Label
    }

    sealed interface Action {
        data object InitialLoadPager : Action
    }
}