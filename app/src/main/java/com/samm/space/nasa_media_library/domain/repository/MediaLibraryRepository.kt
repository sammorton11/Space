package com.samm.space.nasa_media_library.domain.repository

import com.samm.space.core.Resource
import com.samm.space.nasa_media_library.domain.models.NasaLibraryResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MediaLibraryRepository {
    suspend fun getData(query: String): Response<NasaLibraryResponse>
    suspend fun getVideoData(url: String): Response<String>
    fun searchImageVideoLibrary(query: String): Flow<Resource<Response<NasaLibraryResponse>>>
    fun savedQueryFlow(): Flow<String?>

    fun videoDataFlow(url: String): Flow<Resource<Response<String>>>
}