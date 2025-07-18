package org.l3ger0j.presentation.mvi

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.l3ger0j.presentation.mvi.DetailsStore.Intent
import org.l3ger0j.presentation.mvi.DetailsStore.Label
import org.l3ger0j.presentation.mvi.DetailsStore.State

class RealDetailsStore(private val storeFactory: StoreFactory) : KoinComponent {
    fun create(): DetailsStore = DetailsImpl()

    private inner class DetailsImpl :
            DetailsStore,
        Store<Intent, State, Label> by storeFactory.create(
            name = "DetailsStore",
            initialState = State,
            bootstrapper = DetailsBootstrapper(),
            executorFactory = { DetailsExecutor() },
            reducer = DetailsReducer
        )
}