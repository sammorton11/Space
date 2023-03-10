package com.example.space.nasa_media_library.domain.repository

import com.example.space.core.Resource
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.NasaLibraryResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MediaLibraryRepository {
    suspend fun getData(query: String): Response<NasaLibraryResponse>
    suspend fun getVideoData(url: String): Response<String>
    fun searchImageVideoLibrary(query: String): Flow<Resource<Response<NasaLibraryResponse>>>
    fun savedQueryFlow(): Flow<String?>
}