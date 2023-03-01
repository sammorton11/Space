package com.example.space.mars_weather.presentation

import androidx.compose.runtime.Composable
import com.example.space.mars_weather.presentation.view_models.MarsWeatherViewModel

@Composable
fun MarsWeatherScreen(viewModel: MarsWeatherViewModel) {
    MarsWeatherContent(viewModel)
}