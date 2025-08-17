package org.l3ger0j.catalog.presentation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.l3ger0j.catalog.presentation.mvi.CatalogStore
import org.l3ger0j.domain.model.Hero

interface CatalogComponent {
    val model: StateFlow<CatalogStore.State>
    val labels: Flow<CatalogStore.Label>

    fun doRefresh()
    fun filter(filterParam: String)
    fun textSearch(textSearch: String)
    fun moveToOther(dataToView: Hero)
}