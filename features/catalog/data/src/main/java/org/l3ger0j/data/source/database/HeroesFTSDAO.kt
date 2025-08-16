package org.l3ger0j.data.source.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import org.l3ger0j.data.source.database.model.HeroesEntityModel

@Dao
abstract class HeroesFTSDAO {
    @Query("SELECT * FROM heroes JOIN heroes_fts ON heroes.id == heroes_fts.id WHERE heroes_fts.id MATCH :text || '*'")
    abstract fun heroesLikeId(text: String): PagingSource<Int, HeroesEntityModel>

    @Query("SELECT * FROM heroes JOIN heroes_fts ON heroes.id == heroes_fts.id WHERE heroes_fts.name MATCH :text || '*'")
    abstract fun heroesLikeName(text: String): PagingSource<Int, HeroesEntityModel>
}