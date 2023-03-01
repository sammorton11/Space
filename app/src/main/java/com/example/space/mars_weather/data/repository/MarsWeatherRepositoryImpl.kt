package com.example.space.mars_weather.data.repository

import com.example.space.mars_weather.data.MarsWeatherApi
import com.example.space.mars_weather.domain.mars_weather_models.MarsWeatherData
import com.example.space.mars_weather.domain.repository.MarsWeatherRepository
import retrofit2.Response
import javax.inject.Inject

class MarsWeatherRepositoryImpl @Inject constructor(private val api: MarsWeatherApi):
    MarsWeatherRepository {
    override suspend fun getData(): Response<MarsWeatherData> {
        return api.getMarsWeather()
    }
}