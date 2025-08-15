package org.l3ger0j.presentation

import kotlinx.coroutines.flow.StateFlow
import org.l3ger0j.domain.model.Hero
import org.l3ger0j.presentation.mvi.CatalogStore

interface CatalogComponent {
    val model: StateFlow<CatalogStore.State>

    fun doRefresh()
    fun moveToOther(dataToView: Hero)
}