package org.l3ger0j.network

import io.ktor.client.HttpClient
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    singleOf(::createKtorClient) {
        bind<HttpClient>()
    }
}