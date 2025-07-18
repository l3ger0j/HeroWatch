package org.l3ger0j.data.source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.l3ger0j.data.source.database.model.HeroResponseEntityModel

@Dao
interface HeroResponseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceAll(users: List<HeroResponseEntityModel>)

    @Query("Select * FROM hero_response WHERE heroId = :id")
    suspend fun getElementById(id: Int): HeroResponseEntityModel?

    @Query("SELECT * FROM hero_response")
    suspend fun getAll(): List<HeroResponseEntityModel>

    @Query("DELETE FROM hero_response")
    suspend fun clearAll()
}