package org.l3ger0j.data.repository

import io.ktor.client.*
import org.l3ger0j.domain.model.ServerResponse
import org.l3ger0j.domain.repository.HeroRepository
import org.l3ger0j.network.fetchForGet

internal class HeroRepositoryImpl(private val ktorClient: HttpClient) : HeroRepository {

    companion object {
        const val REPO_LINK = "https://rickandmortyapi.com/api/character"
    }

    override suspend fun getAllCharacters(link: String): ServerResponse {
        return if (link.isEmpty() || link.isBlank()) {
            ktorClient.fetchForGet<ServerResponse>(REPO_LINK).getOrDefault(ServerResponse())
        } else {
            ktorClient.fetchForGet<ServerResponse>(link).getOrDefault(ServerResponse())
        }
    }
}