package org.l3ger0j.data.source.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.l3ger0j.data.source.database.model.HeroEntityFTS
import org.l3ger0j.data.source.database.model.HeroResponseEntityModel
import org.l3ger0j.data.source.database.model.HeroesEntityModel

@Database(
    entities = [HeroesEntityModel::class, HeroResponseEntityModel::class, HeroEntityFTS::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun heroes(): HeroesDAO
    abstract fun heroResponse(): HeroResponseDAO
    abstract fun heroesFTSDAO(): HeroesFTSDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "hero_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}