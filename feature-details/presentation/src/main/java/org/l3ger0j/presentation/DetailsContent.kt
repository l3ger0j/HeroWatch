package org.l3ger0j.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberConstraintsSizeResolver
import coil3.request.ImageRequest
import eu.wewox.textflow.material3.TextFlow
import eu.wewox.textflow.material3.TextFlowObstacleAlignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsContent(
    component: DetailsComponent
) {
    val sizeResolver = rememberConstraintsSizeResolver()
    val currentHero = component.hero
    val uriHandler = LocalUriHandler.current

    val originUrl = LinkAnnotation.Url(
        currentHero.origin.url,
        TextLinkStyles(SpanStyle(color = Color.Blue))
    ) {
        val url = (it as LinkAnnotation.Url).url
        uriHandler.openUri(url)
    }

    val locUrl = LinkAnnotation.Url(
        currentHero.location.url,
        TextLinkStyles(SpanStyle(color = Color.Blue))
    ) {
        val url = (it as LinkAnnotation.Url).url
        uriHandler.openUri(url)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(currentHero.name) },
                navigationIcon = {
                    IconButton(onClick = { component.backToCatalog() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { x ->
        Column(
            Modifier
                .fillMaxWidth()
                .padding(x)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
            ) {
                item {
                    val text = buildAnnotatedString {
                        appendLine("Name: ${currentHero.name}")
                        appendLine("Species: ${currentHero.species}")
                        appendLine("Gender: ${currentHero.gender}")
                        appendLine("Status: ${currentHero.status}")
                        if (!currentHero.type.isEmpty()) {
                            appendLine("Type: ${currentHero.type}")
                        }
                        withLink(originUrl) { appendLine("Origin: ${currentHero.origin.name}") }
                        withLink(locUrl) { appendLine("Location: ${currentHero.location.name}") }
                    }
                    TextFlow(
                        text = text,
                        obstacleAlignment = TextFlowObstacleAlignment.TopEnd,
                        obstacleContent = {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalPlatformContext.current)
                                    .data(currentHero.image)
                                    .size(sizeResolver)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        }
                    )
                }
            }
            Text("Episodes:", style = MaterialTheme.typography.titleMedium)
            FlowRow(
                modifier = Modifier.weight(0.5f)
            ) {
                currentHero.episode.forEachIndexed { index, string ->
                    ElevatedAssistChip(
                        onClick = { uriHandler.openUri(string) },
                        label = { Text("Episode $index") }
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun DetailsContentPreview() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = rememberVectorPainter(Icons.Default.Search),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(Modifier.width(16.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            repeat(3) {
                item {
                    Text(
                        text = "Heh = 3",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}