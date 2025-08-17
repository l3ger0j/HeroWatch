package org.l3ger0j.catalog.presentation.mvi

import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import org.l3ger0j.catalog.presentation.mvi.CatalogStore.Label.*
import org.l3ger0j.domain.usecase.GetHeroPagerUseCase
import org.l3ger0j.domain.usecase.OrderByPagerUseCase

class CatalogExecutor(
    private val getHeroPagerUseCase: GetHeroPagerUseCase,
    private val orderByPagerUseCase: OrderByPagerUseCase
) : CoroutineExecutor<CatalogStore.Intent, CatalogStore.Action, CatalogStore.State, CatalogStore.Message, CatalogStore.Label>() {
    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    override fun executeIntent(intent: CatalogStore.Intent) {
        when (intent) {
            is CatalogStore.Intent.RefreshFilterMap -> {
                scope.launch {
                    val flowHeroPager = getHeroPagerUseCase.execute(state().textToSearch)
                    publish(UpdatePagingDataFlow(flowHeroPager))
                }
            }

            is CatalogStore.Intent.OrderTextPaging -> {
                scope.launch {
                    val flowHeroPager = orderByPagerUseCase.execute(intent.orderBy)
                    publish(UpdatePagingDataFlow(flowHeroPager))
                }
            }

            is CatalogStore.Intent.FindTextPaging -> {
                dispatch(CatalogStore.Message.UpdateTextToSearch(intent.textToSearch))
                scope.launch {
                    val flowHeroPager = getHeroPagerUseCase.execute(state().textToSearch)
                    publish(UpdatePagingDataFlow(flowHeroPager))
                }
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun executeAction(action: CatalogStore.Action) {
        when (action) {
            is CatalogStore.Action.InitialLoadPager -> {
                scope.launch {
                    val flowHeroPager = getHeroPagerUseCase.execute(state().textToSearch)
                    publish(UpdatePagingDataFlow(flowHeroPager))
                }
            }
        }
    }
}