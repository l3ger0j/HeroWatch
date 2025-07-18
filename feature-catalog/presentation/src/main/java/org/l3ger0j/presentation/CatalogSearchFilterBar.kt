package org.l3ger0j.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CatalogSearchFilterBar(
    filterMap: HashMap<String, String>,
    component: CatalogComponent
) {
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val filters = listOf("Name", "Species", "Type", "Dimension", "Episode")

    Box(
        Modifier
            .fillMaxWidth()
            .semantics { isTraversalGroup = true }) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = text,
                    onQueryChange = { text = it },
                    onSearch = {
                        expanded = false
                        val uri = "http://dummy/?$text".toUri()
                        val converter = uri.queryParameterNames
                            .associateWith { name ->
                                uri.getQueryParameter(name)
                                    ?.removeSurrounding("\"")
                                    ?.takeUnless { it == "" }
                                    .orEmpty()
                            }
                        filterMap.putAll(converter)
                        component.doRefresh(filterMap)
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Search hero") },
                    leadingIcon = {
                        if (expanded) {
                            IconButton(onClick = {
                                expanded = false
                                val uri = "http://dummy/?$text".toUri()
                                val converter = uri.queryParameterNames
                                    .associateWith { name ->
                                        uri.getQueryParameter(name)
                                            ?.removeSurrounding("\"")
                                            ?.takeUnless { it == "" }
                                            .orEmpty()
                                    }
                                filterMap.putAll(converter)
                                component.doRefresh(filterMap)
                            }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                            }
                        } else {
                            Icon(Icons.Default.Search, contentDescription = null)
                        }
                    },
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filters.forEach { filter ->
                    FilterChip(
                        selected = false,
                        onClick = {
                            val key = filter.lowercase()
                            val prefix = when {
                                text.isBlank() -> ""
                                text.endsWith("&") -> ""
                                else -> "&"
                            }
                            text += "$prefix$key="
                        },
                        label = { Text(filter) }
                    )
                }
            }
        }
    }
}