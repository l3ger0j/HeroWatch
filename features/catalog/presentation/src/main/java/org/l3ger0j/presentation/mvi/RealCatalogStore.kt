package org.l3ger0j.presentation.mvi

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.l3ger0j.data.mapper.ConnectivityChecker
import org.l3ger0j.presentation.mvi.CatalogStore.Action
import org.l3ger0j.presentation.mvi.CatalogStore.Intent
import org.l3ger0j.presentation.mvi.CatalogStore.Label
import org.l3ger0j.presentation.mvi.CatalogStore.State

class RealCatalogStore(private val storeFactory: StoreFactory) : KoinComponent {
    fun create(): CatalogStore =
        object : CatalogStore, Store<Intent, State, Label> by storeFactory.create(
            name = "CatalogStore",
            initialState = State(),
            bootstrapper = coroutineBootstrapper {
                dispatch(Action.InitialLoadPager)
            },
            executorFactory = { CatalogExecutor(get(), ConnectivityChecker(get())) },
            reducer = CatalogReducer
        ) {}
}