package com.samm.space.nasa_media_library.data.network

import com.samm.space.nasa_media_library.domain.models.NasaLibraryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("search?")
    suspend fun fetchData(
        @Query("q")
        query: String
    ): NasaLibraryResponse?
}
