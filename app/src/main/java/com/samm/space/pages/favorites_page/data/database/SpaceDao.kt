package com.samm.space.pages.favorites_page.data.database

import androidx.room.*
import com.samm.space.pages.nasa_media_library_page.domain.models.Item
import com.samm.space.pages.picture_of_the_day_page.domain.models.Apod
import kotlinx.coroutines.flow.Flow

@Dao
interface SpaceDao {

    @Query("SELECT * FROM library_items_table")
    fun getAllLibraryFavorites(): Flow<List<Item>>

    @Query("SELECT * FROM apod")
    fun getAllApodFavorites(): Flow<List<Apod>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(apod: Apod)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(item: Item)

    @Delete
    suspend fun deleteFavorite(apod: Apod)

    @Delete
    suspend fun deleteFavorite(item: Item)
}
