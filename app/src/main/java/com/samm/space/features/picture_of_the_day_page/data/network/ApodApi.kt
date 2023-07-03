package com.samm.space.features.picture_of_the_day_page.data.network

import com.samm.space.core.Constants.API_KEY
import com.samm.space.features.picture_of_the_day_page.domain.models.Apod
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApi {

    @GET("planetary/apod")
    suspend fun getApod(
        @Query("api_key")
        apiKey: String = API_KEY
    ): Apod?

    @GET("planetary/apod")
    suspend fun getApodByDate(
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("date")
        date: String,
    ): Apod?
}