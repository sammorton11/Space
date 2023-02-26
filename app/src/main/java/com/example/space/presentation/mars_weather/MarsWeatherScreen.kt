package com.example.space.presentation.mars_weather

import androidx.compose.runtime.Composable
import com.example.space.presentation.view_model.mars_weather.MarsWeatherViewModel

@Composable
fun MarsWeatherScreen(viewModel: MarsWeatherViewModel) {
    MarsWeatherContent(viewModel)
}