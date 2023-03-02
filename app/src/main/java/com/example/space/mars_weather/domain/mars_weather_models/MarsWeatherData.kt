package com.example.space.mars_weather.domain.mars_weather_models

import com.google.gson.annotations.SerializedName

data class MarsWeatherData(
    @SerializedName("sol_keys")
    val solKeys: List<String>,
    @SerializedName("validity_checks")
    val validityChecks: MarsWeatherValidityChecks
)

data class MarsWeatherValidityChecks(
    @SerializedName("sol_hours_required")
    val solHoursRequired: Int,
    @SerializedName("sols_checked")
    val solsChecked: List<String>,
    @SerializedName("1219")
    val sol1219: MarsWeatherSolValidityCheck
)

data class MarsWeatherSolValidityCheck(
    @SerializedName("AT")
    val atmosphericTemperature: MarsWeatherSensorValidityCheck,
    @SerializedName("HWS")
    val horizontalWindSpeed: MarsWeatherSensorValidityCheck,
    @SerializedName("PRE")
    val atmosphericPressure: MarsWeatherSensorValidityCheck,
    @SerializedName("WD")
    val windDirection: MarsWeatherSensorValidityCheck
)

data class MarsWeatherSensorValidityCheck(
    @SerializedName("sol_hours_with_data")
    val solHoursWithData: List<Int>,
    @SerializedName("valid")
    val isValid: Boolean
)