package org.l3ger0j.presentation

import androidx.compose.runtime.Composable

@Composable
fun CatalogChipRow(
    component: CatalogComponent
) {
    FilterChipGroup(
        items = mutableListOf("Number", "Name", "HP", "Attack", "Defence"),
        onSelectedChanged = { filterPos ->
            when (filterPos) {
                0 -> {

                }

                1 -> {

                }

                2 -> {

                }

                3 -> {

                }

                4 -> {

                }
            }
        }
    )
}