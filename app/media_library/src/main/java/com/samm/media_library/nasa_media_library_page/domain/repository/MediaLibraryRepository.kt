package com.samm.media_library.nasa_media_library_page.domain.repository

import com.samm.core.domain.library_models.Item
import com.samm.core.domain.library_models.NasaLibraryResponse
import com.samm.core.util.Resource
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