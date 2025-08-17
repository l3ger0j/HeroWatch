package org.l3ger0j.herowatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.l3ger0j.catalog.presentation.CatalogContent
import org.l3ger0j.catalog.presentation.di.featureCatalogModules
import org.l3ger0j.details.presentation.DetailsContent
import org.l3ger0j.herowatch.ui.theme.HeroWatchTheme

class MainActivity : ComponentActivity(), KoinComponent {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidContext(this@MainActivity)
                modules(featureCatalogModules)
            }
        }

        val root = RealRootComponent(
            componentContext = defaultComponentContext(),
            preloadAppDBUseCase = getKoin().get()
        )

        if (savedInstanceState == null) {
            CoroutineScope(Dispatchers.IO).launch {
                root.preloadAppDB()
            }
        }

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