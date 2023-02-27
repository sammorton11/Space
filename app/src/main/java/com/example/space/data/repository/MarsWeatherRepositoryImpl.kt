package com.example.space.data.repository

import com.example.space.domain.models.mars_weather_models.MarsWeatherData
import com.example.space.data.network.MarsWeatherApi
import com.example.space.domain.repository.MarsWeatherRepository
import retrofit2.Response
import javax.inject.Inject

class MarsWeatherRepositoryImpl @Inject constructor(private val api: MarsWeatherApi): MarsWeatherRepository {
    override suspend fun getData(): Response<MarsWeatherData> {
        return api.getMarsWeather()
    }
}