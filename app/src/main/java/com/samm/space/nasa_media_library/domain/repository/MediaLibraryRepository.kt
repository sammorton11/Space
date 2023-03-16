package com.samm.space.nasa_media_library.domain.repository

import com.samm.space.core.Resource
import com.samm.space.nasa_media_library.domain.models.NasaLibraryResponse
import kotlinx.coroutines.flow.Flow

interface MediaLibraryRepository {
    suspend fun getData(query: String): NasaLibraryResponse?
    suspend fun getVideoData(url: String): String?
    fun searchImageVideoLibrary(query: String): Flow<Resource<NasaLibraryResponse?>>
    fun savedQueryFlow(): Flow<String?>
    fun videoDataFlow(url: String): Flow<Resource<String?>>
}