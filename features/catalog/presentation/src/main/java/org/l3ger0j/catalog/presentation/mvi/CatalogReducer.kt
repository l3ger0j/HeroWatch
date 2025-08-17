package org.l3ger0j.catalog.presentation.mvi

import com.arkivanov.mvikotlin.core.store.Reducer

object CatalogReducer : Reducer<CatalogStore.State, CatalogStore.Message> {
    override fun CatalogStore.State.reduce(msg: CatalogStore.Message): CatalogStore.State {
        return when (msg) {
            is CatalogStore.Message.UpdateTextToSearch -> copy(msg.textToSearch)
        }
    }
}