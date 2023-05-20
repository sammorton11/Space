package com.samm.apod.picture_of_the_day_page.data.network

import com.samm.core.domain.apod_models.Apod
import com.samm.shared_resources.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApi {
    @GET("planetary/apod")
    suspend fun getApod(
        @Query("api_key")
        apiKey: String = API_KEY
    ): Apod?
}