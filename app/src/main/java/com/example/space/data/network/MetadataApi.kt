package com.example.space.data.network

import retrofit2.http.GET
import retrofit2.http.Url

interface MetadataApi {
    @GET
    suspend fun fetchData(@Url url: String): String
}
