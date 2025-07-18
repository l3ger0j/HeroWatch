package org.l3ger0j.presentation.mvi

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import org.l3ger0j.presentation.mvi.DetailsStore.Intent
import org.l3ger0j.presentation.mvi.DetailsStore.Label
import org.l3ger0j.presentation.mvi.DetailsStore.State
import org.l3ger0j.presentation.mvi.DetailsStore.Action
import org.l3ger0j.presentation.mvi.DetailsStore.Message

class DetailsExecutor() : CoroutineExecutor<Intent, Action, State, Message, Label>() {
    override fun executeIntent(intent: Intent) {
        super.executeIntent(intent)
    }

    override fun executeAction(action: DetailsStore.Action) {
        super.executeAction(action)
    }
}