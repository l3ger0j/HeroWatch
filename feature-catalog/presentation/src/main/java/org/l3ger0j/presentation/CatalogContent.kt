package org.l3ger0j.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberConstraintsSizeResolver
import coil3.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogContent(
    component: CatalogComponent
) {
    val state by component.model.collectAsState()
    val data = state.flowPagingData.collectAsLazyPagingItems()
    val sizeResolver = rememberConstraintsSizeResolver()
    val isRefresh = remember { mutableStateOf(false) }

    val filterMap: HashMap<String, String> = remember { hashMapOf() }

    Scaffold(
        topBar = {
            Column {
                CatalogSearchFilterBar(filterMap, component)
                CatalogChipRow(filterMap, component)
            }
        }
    ) { pad ->
        PullToRefreshBox(
            isRefreshing = isRefresh.value,
            onRefresh = {
                isRefresh.value = true
                component.doRefresh(filterMap)
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(data.itemCount) { index ->
                    data[index]?.let {
                        ListItem(
                            modifier = Modifier.clickable(onClick = { component.moveToOther(it) }),
                            leadingContent = {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalPlatformContext.current)
                                        .data(it.image)
                                        .size(sizeResolver)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                )
                            },
                            headlineContent = { Text(it.name) },
                            supportingContent = { Text("${it.gender}|${it.species}") },
                            trailingContent = { Text(it.status) }
                        )
                    }
                }
                data.loadState.apply {
                    when {
                        refresh is LoadState.NotLoading && data.itemCount < 1 -> {
                            item {
                                ListItem(headlineContent = { Text("No Items") })
                            }
                        }

                        refresh is LoadState.Loading -> {
                            item {
                                isRefresh.value = false
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(20.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        append is LoadState.Loading -> {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth(1f)
                                        .padding(20.dp)
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        refresh is LoadState.Error -> {
                            item {
                                CatalogErrorView(
                                    onClickRetry = { data.retry() },
                                    modifier = Modifier.fillMaxWidth(1f)
                                )
                            }
                        }

                        append is LoadState.Error -> {
                            item {
                                CatalogErrorItem(
                                    onClickRetry = { data.retry() },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}