package org.l3ger0j.presentation.mvi

import com.arkivanov.mvikotlin.core.store.Reducer
import org.l3ger0j.presentation.mvi.DetailsStore.Message
import org.l3ger0j.presentation.mvi.DetailsStore.State

object DetailsReducer : Reducer<State, Message> {
    override fun State.reduce(msg: Message): State {
        TODO()
    }
}