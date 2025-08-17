package org.l3ger0j.catalog.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.l3ger0j.catalog.presentation.mvi.CatalogStore
import org.l3ger0j.catalog.presentation.mvi.RealCatalogStore
import org.l3ger0j.domain.model.Hero

class RealCatalogComponent(
    private val componentContext: ComponentContext,
    val moveToDetails: (Hero) -> Unit,
) : ComponentContext by componentContext, CatalogComponent {

    private val store = instanceKeeper.getStore {
        RealCatalogStore(DefaultStoreFactory()).create(stateKeeper)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<CatalogStore.State> = store.stateFlow
    override val labels: Flow<CatalogStore.Label> = store.labels

    override fun doRefresh() {
        store.accept(CatalogStore.Intent.RefreshFilterMap)
    }

    override fun filter(filterParam: String) {
        store.accept(CatalogStore.Intent.OrderTextPaging(filterParam))
    }

    override fun textSearch(textSearch: String) {
        store.accept(CatalogStore.Intent.FindTextPaging(textSearch))
    }

    override fun moveToOther(dataToView: Hero) {
        moveToDetails.invoke(dataToView)
    }
}