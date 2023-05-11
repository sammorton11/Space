package com.samm.space.pages.favorites_page.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.samm.space.pages.nasa_media_library_page.domain.models.Item
import com.samm.space.pages.nasa_media_library_page.domain.models.NasaLibraryResponse
import com.samm.space.pages.picture_of_the_day_page.domain.models.Apod

@Database(entities = [Item::class, Apod::class], version = 1, exportSchema = false)
abstract class SpaceExplorerDatabase: RoomDatabase() {
    abstract fun myDao(): SpaceDao
}