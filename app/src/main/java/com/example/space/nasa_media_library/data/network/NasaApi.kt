package com.example.space.nasa_media_library.data.network

import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.NasaLibraryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("search?")
    suspend fun fetchData(
        @Query("q")
        query: String
    ): Response<NasaLibraryResponse>
}
