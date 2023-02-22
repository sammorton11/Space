package com.example.space.domain.repository

import com.example.space.domain.models.Collection
import com.example.space.domain.models.Metadata
import com.example.space.domain.models.NasaLibraryResponse
import retrofit2.Call
import retrofit2.Response

interface Repository {
    suspend fun getData(query: String): Response<NasaLibraryResponse>
    suspend fun getVideoData(url: String): Response<String>
}