package com.example.space.data.network

import com.example.space.domain.models.Collection
import com.example.space.domain.models.NasaLibraryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaApi {
    @GET("search?")
    suspend fun fetchData(
        @Query("q")
        query: String
    ): Response<NasaLibraryResponse>
}
