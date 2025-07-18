package org.l3ger0j.herowatch

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import org.l3ger0j.domain.model.Hero
import org.l3ger0j.presentation.RealCatalogComponent
import org.l3ger0j.presentation.RealDetailsComponent

class RealRootComponent(
    private val componentContext: ComponentContext
) : ComponentContext by componentContext, RootComponent {

    private val navigation = StackNavigation<ChildConfig>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = null,
            initialConfiguration = ChildConfig.Catalog,
            handleBackButton = true,
            childFactory = ::createChild
        )

    @OptIn(DelicateDecomposeApi::class)
    private fun createChild(
        config: ChildConfig,
        componentContext: ComponentContext
    ): RootComponent.Child = when (config) {
        is ChildConfig.Catalog -> {
            RootComponent.Child.CatalogChild(RealCatalogComponent(componentContext) { navigation.push(ChildConfig.Details(it)) })
        }

        is ChildConfig.Details -> {
            RootComponent.Child.DetailsChild(RealDetailsComponent(componentContext, config.dataShow) { navigation.pop() })
        }
    }

    @Serializable
    private sealed class ChildConfig {
        @Serializable
        data object Catalog : ChildConfig()

        @Serializable
        data class Details(val dataShow: Hero) : ChildConfig()
    }
}