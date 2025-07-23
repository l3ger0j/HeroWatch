package org.l3ger0j.presentation.mvi

import com.arkivanov.mvikotlin.core.store.Store
import org.l3ger0j.presentation.mvi.DetailsStore.Intent
import org.l3ger0j.presentation.mvi.DetailsStore.Label
import org.l3ger0j.presentation.mvi.DetailsStore.State

interface DetailsStore : Store<Intent, State, Label> {
    object State

    sealed interface Intent

    sealed interface Message

    sealed interface Label

    sealed interface Action
}