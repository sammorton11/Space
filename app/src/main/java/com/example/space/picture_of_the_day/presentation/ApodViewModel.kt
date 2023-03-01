package com.example.space.picture_of_the_day.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.space.core.Resource
import com.example.space.picture_of_the_day.domain.repository.ApodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ApodViewModel @Inject constructor(private val repository: ApodRepository): ViewModel() {

    private lateinit var errorMessage: String
    val _state: MutableState<ApodState> = mutableStateOf(ApodState())
    val state: State<ApodState> = _state


    private fun getApodData() = flow {
        emit(Resource.Loading())
        val response = repository.getData()
        Log.d("response", response.toString())
        errorMessage = response.errorBody().toString()
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(errorMessage))
    }

    fun getApodState() {
        getApodData().onEach { response ->
            when(response) {
                is Resource.Loading -> {
                    _state.value = ApodState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = ApodState(data = response.data?.body())
                }
                is Resource.Error -> {
                    _state.value = ApodState(error = response.data?.errorBody().toString())
                }
            }
        }.launchIn(viewModelScope)
    }
}