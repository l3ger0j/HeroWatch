package org.l3ger0j.data.source.database.model

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = HeroesEntityModel::class)
@Entity(tableName = "heroes_fts")
class HeroEntityFTS(val id: Int, val name: String)