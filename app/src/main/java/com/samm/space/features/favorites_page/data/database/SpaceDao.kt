package com.samm.space.features.favorites_page.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samm.space.features.nasa_media_library_page.domain.models.Item
import com.samm.space.features.picture_of_the_day_page.domain.models.Apod
import kotlinx.coroutines.flow.Flow

@Dao
interface SpaceDao {

    @Query("SELECT * FROM library_items_table")
    fun getAllLibraryFavorites(): Flow<List<Item>>

    @Query("SELECT * FROM apod")
    fun getAllApodFavorites(): Flow<List<Apod>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(apod: Apod)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(item: Item)

    @Delete
    suspend fun deleteFavorite(apod: Apod)

    @Delete
    suspend fun deleteFavorite(item: Item)

    @Query("DELETE FROM library_items_table")
    suspend fun deleteAllFavorites()

    @Query("UPDATE library_items_table SET favorite = :favorite WHERE id = :itemId")
    suspend fun updateFavoriteState(itemId: Int, favorite: Int)
}
