package com.samm.space.picture_of_the_day.data

import com.samm.space.core.Constants.API_KEY
import com.samm.space.picture_of_the_day.domain.models.Apod
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApi {

    @GET("planetary/apod")
    suspend fun getApod(
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<Apod>
}