package org.l3ger0j.catalog.presentation

import androidx.compose.runtime.Composable

@Composable
fun CatalogChipRow(
    component: CatalogComponent
) {
    FilterChipGroup(
        items = mutableListOf("Number", "Name"),
        onSelectedChanged = { filterPos ->
            when (filterPos) {
                0 -> {
                    component.filter("id")
                }

                1 -> {
                    component.filter("name")
                }
            }
        }
    )
}