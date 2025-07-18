package org.l3ger0j.data.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.l3ger0j.data.repository.HeroRepositoryImpl
import org.l3ger0j.data.source.database.AppDatabase
import org.l3ger0j.domain.repository.HeroRepository

val dataModule = module {
    singleOf(::HeroRepositoryImpl) {
        bind<HeroRepository>()
    }
    single { AppDatabase.getInstance(get()) }
}