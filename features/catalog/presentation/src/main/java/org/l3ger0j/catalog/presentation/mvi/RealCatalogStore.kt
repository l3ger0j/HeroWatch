package org.l3ger0j.catalog.presentation.mvi

import com.arkivanov.essenty.statekeeper.StateKeeper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class RealCatalogStore(private val storeFactory: StoreFactory) : KoinComponent {
    fun create(stateKeeper: StateKeeper): CatalogStore = object : CatalogStore,
        Store<CatalogStore.Intent, CatalogStore.State, CatalogStore.Label> by storeFactory.create(
            name = "CatalogStore",
            initialState = stateKeeper.consume("CatalogStoreState", CatalogStore.State.serializer())
                ?: CatalogStore.State(),
            bootstrapper = coroutineBootstrapper {
                dispatch(CatalogStore.Action.InitialLoadPager)
            },
            executorFactory = { CatalogExecutor(get(), get()) },
            reducer = CatalogReducer
        ) {
    }.also {
        stateKeeper.register(key = "CatalogStoreState", CatalogStore.State.serializer()) {
            it.state.copy(textToSearch = "")
        }
    }
}