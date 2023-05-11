package com.samm.space.pages.favorites_page.domain

import com.samm.space.pages.nasa_media_library_page.domain.models.Item
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getAllLibraryFavorites(): Flow<List<Item>>
    suspend fun insertFavorite(item: Item)
    suspend fun deleteFavorite(item: Item)
}