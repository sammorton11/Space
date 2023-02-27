package com.example.space.data.network

import com.example.space.core.Constants.API_KEY
import com.example.space.domain.models.mars_weather_models.MarsWeatherData
import io.ktor.client.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarsWeatherApi {
    @GET("insight_weather/")
    suspend fun getMarsWeather(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("feedtype") feedType: String = "json",
        @Query("ver") version: Double = 1.0
    ): Response<MarsWeatherData>
}
