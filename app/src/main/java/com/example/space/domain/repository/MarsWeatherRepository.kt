package com.example.space.domain.repository

import com.example.space.domain.models.mars_weather_models.MarsWeatherData
import retrofit2.Response

interface MarsWeatherRepository {
    suspend fun getData(): Response<MarsWeatherData>
}