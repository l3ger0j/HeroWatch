package org.l3ger0j.data.repository

import androidx.core.text.isDigitsOnly
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import co.pokeapi.pokekotlin.PokeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import org.l3ger0j.data.mapper.ConnectivityChecker
import org.l3ger0j.data.mapper.mapToDomain
import org.l3ger0j.data.source.database.AppDatabase
import org.l3ger0j.data.source.database.model.HeroesEntityModel
import org.l3ger0j.domain.model.Hero
import org.l3ger0j.domain.repository.HeroRepository

internal class HeroRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val connectivityChecker: ConnectivityChecker
) : HeroRepository {
    override suspend fun preloadAppDB() {
        if (!connectivityChecker.isOnline()) {
            return
        }

        val emptyList = PokeApi.getPokemonVarietyList(0, 0)
        val pokemonVarietyList = PokeApi.getPokemonVarietyList(0, emptyList.count)
        pokemonVarietyList.results.forEach { pokemon ->
            supervisorScope {
                val variety = withContext(Dispatchers.IO) { PokeApi.getPokemonVariety(pokemon.id) }
                appDatabase.heroes().insertReplaceSingle(
                    HeroesEntityModel(
                        id = variety.id,
                        name = variety.name,
                        types = variety.types.map { (_, named) -> named.name },
                        spriteFrontDefault = variety.sprites.frontDefault ?: ""
                    )
                )
            }
        }
    }

    override suspend fun orderHeroPager(orderBy: String): Flow<PagingData<Hero>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50, enablePlaceholders = false
            ),
            pagingSourceFactory = { appDatabase.heroes().orderBy(orderBy) },
        ).flow.map { value -> value.map { entityModel -> entityModel.mapToDomain() } }
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getHeroPager(textToSearch: String): Flow<PagingData<Hero>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50, enablePlaceholders = false
            ),
            pagingSourceFactory = {
                if (textToSearch.isBlank()) {
                    appDatabase.heroes().all()
                } else {
                    if (textToSearch.isDigitsOnly()) {
                        val convStr = textToSearch.replace("^0+(?!$)".toRegex(), "")
                        appDatabase.heroesFTSDAO().heroesLikeId(convStr)
                    } else {
                        appDatabase.heroesFTSDAO().heroesLikeName(textToSearch)
                    }
                }
            },
        ).flow.map { value -> value.map { entityModel -> entityModel.mapToDomain() } }
    }
}