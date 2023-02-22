package com.example.space.data.network

import com.example.space.domain.models.Metadata
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface MetadataApi {
    @GET
    suspend fun fetchData(@Url url: String): Response<String>
}
