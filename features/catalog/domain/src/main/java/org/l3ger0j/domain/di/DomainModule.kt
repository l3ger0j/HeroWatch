package org.l3ger0j.domain.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.l3ger0j.domain.usecase.GetHeroPagerUseCase
import org.l3ger0j.domain.usecase.OrderByPagerUseCase
import org.l3ger0j.domain.usecase.PreloadAppDBUseCase

val domainModule = module {
    singleOf(::GetHeroPagerUseCase)
    singleOf(::OrderByPagerUseCase)
    singleOf(::PreloadAppDBUseCase)
}