package com.example.space.domain.repository

import com.example.space.domain.models.NasaLibraryResponse
import retrofit2.Response

interface MediaLibraryRepository {
    suspend fun getData(query: String): Response<NasaLibraryResponse>
    suspend fun getVideoData(url: String): Response<String>
}