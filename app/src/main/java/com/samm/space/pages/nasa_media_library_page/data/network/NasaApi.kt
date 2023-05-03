package com.samm.space.pages.nasa_media_library_page.data.network

import com.samm.space.pages.nasa_media_library_page.domain.models.NasaLibraryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("search?")
    suspend fun fetchData(
        @Query("q")
        query: String
    ): NasaLibraryResponse?
}
