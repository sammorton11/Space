package com.example.space.nasa_media_library.domain.repository

import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.NasaLibraryResponse
import retrofit2.Response

interface MediaLibraryRepository {
    suspend fun getData(query: String): Response<NasaLibraryResponse>
    suspend fun getVideoData(url: String): Response<String>
}