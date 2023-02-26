package com.example.space

import com.example.space.domain.models.mars_weather_models.MarsWeatherData

data class MarsWeatherState(
    val isLoading: Boolean = false,
    val data: MarsWeatherData? = null,
    val error: String = ""
)
