package org.l3ger0j.presentation.mvi

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import kotlinx.coroutines.launch
import org.l3ger0j.data.mapper.mapToDomain
import org.l3ger0j.data.source.database.AppDatabase

class DetailsBootstrapper(
): CoroutineBootstrapper<DetailsStore.Action>() {
    override fun invoke() {

    }
}