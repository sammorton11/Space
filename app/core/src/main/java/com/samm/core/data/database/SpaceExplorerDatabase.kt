package com.samm.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.samm.core.domain.apod_models.Apod
import com.samm.core.domain.library_models.Item

@Database(entities = [Item::class, Apod::class], version = 4, exportSchema = false)
abstract class SpaceExplorerDatabase: RoomDatabase() {
    abstract fun myDao(): SpaceDao
}