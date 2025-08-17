package org.l3ger0j.data.source.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.l3ger0j.data.source.database.converters.ListTypeConverter
import org.l3ger0j.data.source.database.model.HeroEntityFTS
import org.l3ger0j.data.source.database.model.HeroesEntityModel

@Database(
    entities = [HeroesEntityModel::class, HeroEntityFTS::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [ListTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun heroes(): HeroesDAO
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