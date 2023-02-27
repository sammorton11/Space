package com.example.space.presentation.mars_weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.space.presentation.mars_weather.view_models.MarsWeatherViewModel

// In-Progress
@Composable
fun MarsWeatherContent(viewModel: MarsWeatherViewModel) {

    val state = viewModel.state
    val weatherData = state.value.data

    Column(Modifier.fillMaxSize()) {
        Column(Modifier.padding(15.dp)) {
            weatherData?.let { data ->
                Text("Sol Keys: ${data.solKeys}")
                Text(text = data.validityChecks.solHoursRequired.toString())
                Text(text = data.validityChecks.solsChecked.toString())
                Text(text = data.validityChecks.sol1219.windDirection.isValid.toString())
                Text(text = data.validityChecks.sol1219.atmosphericPressure.solHoursWithData.toString())
            }
        }
        Button(onClick = { viewModel.getData() }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Get Mars Weather Data")
        }
    }
}