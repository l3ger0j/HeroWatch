package org.l3ger0j.data.repository

import io.ktor.client.*
import org.l3ger0j.domain.model.ServerResponse
import org.l3ger0j.domain.repository.HeroRepository
import org.l3ger0j.network.fetchForGet

internal class HeroRepositoryImpl(private val ktorClient: HttpClient) : HeroRepository {

    companion object {
        const val REPO_LINK = "https://rickandmortyapi.com/api/character"
    }

    override suspend fun getAllCharacters(link: String, filterMap: HashMap<String, String>): ServerResponse {
        return if (link.isEmpty() || link.isBlank()) {
            if (filterMap.isEmpty()) {
                ktorClient.fetchForGet<ServerResponse>(REPO_LINK).getOrDefault(ServerResponse())
            } else {
                ktorClient.fetchForGet<ServerResponse>("$REPO_LINK/?${filterMap.entries.joinToString(separator = "&")}").getOrDefault(ServerResponse())
            }
        } else {
            if (filterMap.isEmpty()) {
                ktorClient.fetchForGet<ServerResponse>(link).getOrDefault(ServerResponse())
            } else {
                val entriesConv = filterMap.entries.joinToString(separator = "&")
                if (link.contains(entriesConv, ignoreCase = true)) {
                    ktorClient.fetchForGet<ServerResponse>(link).getOrDefault(ServerResponse())
                } else {
                    ktorClient.fetchForGet<ServerResponse>("$link&$entriesConv").getOrDefault(ServerResponse())
                }
            }
        }
    }
}