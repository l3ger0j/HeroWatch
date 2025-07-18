package org.l3ger0j.herowatch

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import org.l3ger0j.presentation.CatalogComponent
import org.l3ger0j.presentation.DetailsComponent


interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class CatalogChild(val component: CatalogComponent) : Child()
        class DetailsChild(val component: DetailsComponent) : Child()
    }
}