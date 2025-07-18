package org.l3ger0j.herowatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.l3ger0j.herowatch.ui.theme.HeroWatchTheme
import org.l3ger0j.network.networkModule
import org.l3ger0j.presentation.CatalogContent
import org.l3ger0j.presentation.DetailsContent
import org.l3ger0j.presentation.RealCatalogComponent
import org.l3ger0j.presentation.RealDetailsComponent
import org.l3ger0j.presentation.di.featureCatalogModules

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidContext(this@MainActivity)
                modules(networkModule)
                modules(featureCatalogModules)
            }
        }

        val root = RealRootComponent(
            componentContext = defaultComponentContext()
        )

        setContent {
            HeroWatchTheme {
                Children(
                    stack = root.childStack
                ) { child ->
                    when (val instance = child.instance) {
                        is RootComponent.Child.CatalogChild -> {
                            CatalogContent(instance.component)
                        }
                        is RootComponent.Child.DetailsChild -> {
                            DetailsContent(instance.component)
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    device = "spec:width=411dp,height=891dp",
    showSystemUi = true
)
@Composable
fun GreetingPreview() {
    HeroWatchTheme {

    }
}