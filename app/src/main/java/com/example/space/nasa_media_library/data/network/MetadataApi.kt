package com.example.space.nasa_media_library.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface MetadataApi {
    @GET
    suspend fun fetchData(@Url url: String): Response<String>
}
