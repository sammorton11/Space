package com.example.space.mars_weather.domain.repository

import com.example.space.mars_weather.domain.mars_weather_models.MarsWeatherData
import retrofit2.Response

interface MarsWeatherRepository {
    suspend fun getData(): Response<MarsWeatherData>
}