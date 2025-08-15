package org.l3ger0j.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CatalogChipRow(
    filterMap: HashMap<String, String>,
    component: CatalogComponent
) {
    var isMenuStatusExpand by remember { mutableStateOf(false) }
    var isMenuGenderExpand by remember { mutableStateOf(false) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                onClick = { isMenuStatusExpand = true },
                label = { Text("status") },
                selected = false,
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = ""
                    )
                }
            )
            DropdownMenu(
                expanded = isMenuStatusExpand,
                onDismissRequest = { isMenuStatusExpand = false }
            ) {
                DropdownMenuItem(text = { Text("alive") }, onClick = {
                    if (filterMap.containsKey("status")) {
                        filterMap.remove("status")
                    } else {
                        filterMap["status"] = "alive"
                    }
                    isMenuStatusExpand = false
                    component.doRefresh(filterMap)
                }, leadingIcon = {
                    val value = filterMap.get("status")
                    if (value != null && value == "alive") {
                        Icon(Icons.Default.Check, contentDescription = null)
                    }
                })
                DropdownMenuItem(text = { Text("dead") }, onClick = {
                    if (filterMap.containsKey("status")) {
                        filterMap.remove("status")
                    } else {
                        filterMap["status"] = "dead"
                    }
                    isMenuStatusExpand = false
                    component.doRefresh(filterMap)
                }, leadingIcon = {
                    val value = filterMap.get("status")
                    if (value != null && value == "dead") {
                        Icon(Icons.Default.Check, contentDescription = null)
                    }
                })
                DropdownMenuItem(text = { Text("unknown") }, onClick = {
                    if (filterMap.containsKey("status")) {
                        filterMap.remove("status")
                    } else {
                        filterMap["status"] = "unknown"
                    }
                    isMenuStatusExpand = false
                    component.doRefresh(filterMap)
                }, leadingIcon = {
                    val value = filterMap.get("status")
                    if (value != null && value == "unknown") {
                        Icon(Icons.Default.Check, contentDescription = null)
                    }
                })
            }
        }
        item {
            FilterChip(
                onClick = { isMenuGenderExpand = true },
                label = { Text("gender") },
                selected = false,
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = ""
                    )
                }
            )
            DropdownMenu(
                expanded = isMenuGenderExpand,
                onDismissRequest = { isMenuGenderExpand = false }
            ) {
                DropdownMenuItem(text = { Text("female") }, onClick = {
                    if (filterMap.containsKey("gender")) {
                        filterMap.remove("gender")
                    } else {
                        filterMap["gender"] = "female"
                    }
                    isMenuGenderExpand = false
                    component.doRefresh(filterMap)
                }, leadingIcon = {
                    val value = filterMap.get("gender")
                    if (value != null && value == "female") {
                        Icon(Icons.Default.Check, contentDescription = null)
                    }
                })
                DropdownMenuItem(text = { Text("male") }, onClick = {
                    if (filterMap.containsKey("gender")) {
                        filterMap.remove("gender")
                    } else {
                        filterMap["gender"] = "male"
                    }
                    isMenuGenderExpand = false
                    component.doRefresh(filterMap)
                }, leadingIcon = {
                    val value = filterMap.get("gender")
                    if (value != null && value == "male") {
                        Icon(Icons.Default.Check, contentDescription = null)
                    }
                })
                DropdownMenuItem(text = { Text("genderless") }, onClick = {
                    if (filterMap.containsKey("gender")) {
                        filterMap.remove("gender")
                    } else {
                        filterMap["gender"] = "genderless"
                    }
                    isMenuGenderExpand = false
                    component.doRefresh(filterMap)
                }, leadingIcon = {
                    val value = filterMap.get("gender")
                    if (value != null && value == "genderless") {
                        Icon(Icons.Default.Check, contentDescription = null)
                    }
                })
                DropdownMenuItem(
                    text = { Text("unknown") }, onClick = {
                        if (filterMap.containsKey("gender")) {
                            filterMap.remove("gender")
                        } else {
                            filterMap["gender"] = "unknown"
                        }
                        isMenuGenderExpand = false
                        component.doRefresh(filterMap)
                    },
                    leadingIcon = {
                        val value = filterMap.get("gender")
                        if (value != null && value == "unknown") {
                            Icon(Icons.Default.Check, contentDescription = null)
                        }
                    })
            }
        }
    }
}