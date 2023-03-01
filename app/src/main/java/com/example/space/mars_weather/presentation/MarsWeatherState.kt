package com.example.space.mars_weather.presentation

import com.example.space.mars_weather.domain.mars_weather_models.MarsWeatherData

data class MarsWeatherState(
    val isLoading: Boolean = false,
    val data: MarsWeatherData? = null,
    val error: String = ""
)
