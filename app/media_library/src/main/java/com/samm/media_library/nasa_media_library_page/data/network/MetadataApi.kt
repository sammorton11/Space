package com.samm.media_library.nasa_media_library_page.data.network

import retrofit2.http.GET
import retrofit2.http.Url

interface MetadataApi {
    @GET
    suspend fun fetchData(@Url url: String): String?
}
