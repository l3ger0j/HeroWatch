package org.l3ger0j.data.source.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.l3ger0j.data.source.database.model.HeroesEntityModel

@Dao
interface HeroesDAO {
    @Query("SELECT * FROM heroes")
    fun all(): PagingSource<Int, HeroesEntityModel>

    @Query("SELECT * FROM heroes")
    fun filtered(): PagingSource<Int, HeroesEntityModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceAll(users: List<HeroesEntityModel>)

    @Query("DELETE FROM heroes")
    suspend fun clearAll()
}