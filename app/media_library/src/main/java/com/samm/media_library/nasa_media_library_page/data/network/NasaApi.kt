package com.samm.media_library.nasa_media_library_page.data.network

import com.samm.core.domain.library_models.NasaLibraryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("search?")
    suspend fun fetchData(
        @Query("q")
        query: String
    ): NasaLibraryResponse?
}
