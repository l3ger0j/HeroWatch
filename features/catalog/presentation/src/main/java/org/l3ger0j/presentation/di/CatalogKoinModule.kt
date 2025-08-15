package org.l3ger0j.presentation.di

import org.l3ger0j.data.di.dataModule
import org.l3ger0j.domain.di.domainModule

val featureCatalogModules = listOf(
    domainModule,
    dataModule
)