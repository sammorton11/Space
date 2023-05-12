package com.samm.space.pages.nasa_media_library_page.domain.repository

import com.samm.space.core.Resource
import com.samm.space.pages.nasa_media_library_page.domain.models.Item
import com.samm.space.pages.nasa_media_library_page.domain.models.NasaLibraryResponse
import kotlinx.coroutines.flow.Flow

interface MediaLibraryRepository {
    suspend fun getData(query: String): NasaLibraryResponse?
    fun getAllFavorites(): Flow<List<Item>>
    suspend fun getVideoData(url: String): String?
    fun searchImageVideoLibrary(query: String): Flow<Resource<NasaLibraryResponse?>>
    fun savedQueryFlow(): Flow<String?>
    fun videoDataFlow(url: String): Flow<Resource<String?>>
    suspend fun insertFavorite(item: Item)
    suspend fun deleteFavorite(item: Item)
    suspend fun updateFavorite(itemId: Int, isFavorite: Boolean)
}