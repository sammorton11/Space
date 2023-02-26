package com.example.space.presentation.mars_weather

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.space.presentation.view_model.mars_weather.MarsWeatherViewModel

@Composable
fun MarsWeatherContent(viewModel: MarsWeatherViewModel) {

    val state = viewModel.state
    val weatherData = state.value.data

    Column() {
        Button(onClick = { viewModel.getData() }) {
            Text(text = "Get Weather")
        }
        if (weatherData != null) {
            if (weatherData.validityChecks?.solHoursRequired != null)
                Text(text = weatherData.validityChecks.solHoursRequired.toString())
        }
    }
}