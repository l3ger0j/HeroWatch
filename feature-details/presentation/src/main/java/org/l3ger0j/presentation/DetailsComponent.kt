package org.l3ger0j.presentation

import kotlinx.coroutines.flow.StateFlow
import org.l3ger0j.domain.model.Hero
import org.l3ger0j.presentation.mvi.DetailsStore

interface DetailsComponent {
    val model: StateFlow<DetailsStore.State>
    val hero: Hero

    fun backToCatalog()
}