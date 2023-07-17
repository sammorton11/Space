package com.samm.space.features.favorites_page.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.samm.space.features.nasa_media_library_page.domain.models.Item
import com.samm.space.features.picture_of_the_day_page.domain.models.Apod

@Database(entities = [Item::class, Apod::class], version = 5, exportSchema = false)
abstract class SpaceExplorerDatabase: RoomDatabase() {
    abstract fun myDao(): SpaceDao
}