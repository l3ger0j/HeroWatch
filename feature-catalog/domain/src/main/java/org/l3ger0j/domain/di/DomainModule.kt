package org.l3ger0j.domain.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.l3ger0j.domain.usecase.FilterAllCharactersUseCase

val domainModule = module {
    singleOf(::FilterAllCharactersUseCase)
}