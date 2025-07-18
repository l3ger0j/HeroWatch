package org.l3ger0j.data.source.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import org.l3ger0j.data.source.database.model.HeroesEntityModel

@Dao
interface HeroesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceAll(users: List<HeroesEntityModel>)

    @Query("SELECT * FROM heroes")
    suspend fun getAll(): List<HeroesEntityModel>

    @Query("SELECT * FROM heroes")
    fun getAllPaging(): PagingSource<Int, HeroesEntityModel>

    @RawQuery(observedEntities = [HeroesEntityModel::class])
    fun getAllSearchPaging(query: SupportSQLiteQuery): PagingSource<Int, HeroesEntityModel>

    @Query("DELETE FROM heroes")
    suspend fun clearAll()
}