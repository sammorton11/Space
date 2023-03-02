package com.example.space.mars_weather.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.space.mars_weather.presentation.view_models.MarsWeatherViewModel
import com.example.space.presentation.ErrorText
import com.example.space.presentation.ProgressBar

// In-Progress
@Composable
fun MarsWeatherContent(viewModel: MarsWeatherViewModel) {

    val state = viewModel.state
    val weatherData = state.value.data
    val error = state.value.error
    val sol1219 = weatherData?.validityChecks?.sol1219

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        
        Text(text = "This Screen is in-progress", color = Color.Red)

        Column(Modifier.padding(15.dp)) {
            when {
                state.value.isLoading -> {
                    ProgressBar()
                }
                weatherData != null -> {
                    Text("Sol Keys: ${weatherData.solKeys}")
                    Text(text = weatherData.validityChecks.solHoursRequired.toString())
                    Text(text = weatherData.validityChecks.solsChecked.toString())
                    Text(text = sol1219?.windDirection?.isValid.toString())
                    sol1219?.atmosphericTemperature?.solHoursWithData?.forEach {
                        Text(text = it.toString())
                    }
                    sol1219?.windDirection?.solHoursWithData?.forEach {
                        Text(text = it.toString())
                    }
                }
                error.isNotBlank() -> {
                    ErrorText(error = error)
                }
            }
        }
    }
}