package org.l3ger0j.data.mapper

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.l3ger0j.domain.model.Hero

class HeroPagingSource(
    private val listHero: List<Hero>
) : PagingSource<String, Hero>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Hero> {
        return try {
            LoadResult.Page(
                data = listHero,
                prevKey = null,
                nextKey = params.key
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Hero>): String? {
        return null
    }
}