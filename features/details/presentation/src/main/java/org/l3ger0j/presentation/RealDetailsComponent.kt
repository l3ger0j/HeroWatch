package org.l3ger0j.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import org.l3ger0j.domain.model.Hero
import org.l3ger0j.presentation.mvi.DetailsStore
import org.l3ger0j.presentation.mvi.RealDetailsStore

class RealDetailsComponent(
    private val componentContext: ComponentContext,
    dataToView: Hero,
    val backToCatalog: () -> Unit
) : ComponentContext by componentContext, DetailsComponent {

    private val store = instanceKeeper.getStore {
        RealDetailsStore(DefaultStoreFactory()).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<DetailsStore.State> = store.stateFlow

    override val hero: Hero = dataToView

    override fun backToCatalog() {
        backToCatalog.invoke()
    }

}