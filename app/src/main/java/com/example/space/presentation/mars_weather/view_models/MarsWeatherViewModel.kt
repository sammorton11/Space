package com.example.space.presentation.mars_weather.view_models

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.space.presentation.mars_weather.MarsWeatherState
import com.example.space.core.Resource
import com.example.space.data.repository.MarsWeatherRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MarsWeatherViewModel @Inject constructor (private val repository: MarsWeatherRepositoryImpl): ViewModel() {

    private val _state = mutableStateOf(MarsWeatherState()) // not exposed because mutable
    val state: State<MarsWeatherState> = _state // expose this to composable because immutable

    private fun getWeatherData() = flow {
        emit(Resource.Loading())
        val response = repository.getData()
        val rawJson = response.body()?.toString() ?: ""
        val code = response.code().toString()
        Log.d("RAW JSON RESPONSE", rawJson)
        Log.d("Response Message", code)
        emit(Resource.Success(response))

    }.catch { error ->
        emit(Resource.Error(error.toString()))
    }

    fun getData() {
        getWeatherData().onEach { response ->

            when(response) {
                is Resource.Success -> {
                    Log.d("Response Success", response.data?.body().toString())
                    _state.value = response.data?.body()?.let {
                        MarsWeatherState(
                            data = it
                        )
                    }!!
                }
                is Resource.Error -> {
                    _state.value = MarsWeatherState(
                        error = response.data?.errorBody().toString()
                    )
                    Log.d("ITEM ERROR", "${response.message}")
                }
                is Resource.Loading -> {
                    _state.value = MarsWeatherState(isLoading = true)
                    Log.d("ITEM LOADING", "true")
                }
            }
        }.launchIn(viewModelScope)
    }
}