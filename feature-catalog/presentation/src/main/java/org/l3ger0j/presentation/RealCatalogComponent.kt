package org.l3ger0j.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import org.l3ger0j.data.mapper.mapToDomain
import org.l3ger0j.data.source.database.model.HeroesEntityModel
import org.l3ger0j.domain.model.Hero
import org.l3ger0j.presentation.mvi.CatalogStore
import org.l3ger0j.presentation.mvi.RealCatalogStore

class RealCatalogComponent(
    private val componentContext: ComponentContext,
    val moveToDetails: (Hero) -> Unit
) : ComponentContext by componentContext, CatalogComponent {

    private val store = instanceKeeper.getStore {
        RealCatalogStore(DefaultStoreFactory()).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<CatalogStore.State> = store.stateFlow

    override fun doRefresh(filter: HashMap<String, String>) {
        store.accept(CatalogStore.Intent.RefreshPagingDataFlow(filter))
    }

    override fun moveToOther(dataToView: Hero) {
        moveToDetails.invoke(dataToView)
    }
}