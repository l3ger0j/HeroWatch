package org.l3ger0j.presentation.mvi

import com.arkivanov.mvikotlin.core.store.Reducer
import org.l3ger0j.presentation.mvi.CatalogStore.Message
import org.l3ger0j.presentation.mvi.CatalogStore.State

object CatalogReducer : Reducer<State, Message> {
    override fun State.reduce(msg: Message): State {
        return when (msg) {
            is Message.UpdatePagingDataFlow -> copy(msg.flowPager)
        }
    }
}