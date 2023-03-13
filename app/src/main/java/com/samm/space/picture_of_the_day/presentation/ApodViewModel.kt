package com.samm.space.picture_of_the_day.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samm.space.core.Resource
import com.samm.space.picture_of_the_day.domain.repository.ApodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ApodViewModel @Inject constructor(private val repository: ApodRepository): ViewModel() {

    val _state: MutableState<ApodState> = mutableStateOf(ApodState())
    val state: State<ApodState> = _state
    init {
        getApodState()
    }

    fun getApodState() {

            repository.getApodData().onEach { response ->
                when(response) {
                    is Resource.Loading -> {
                        _state.value = ApodState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = ApodState(data = response.data?.body())
                    }
                    is Resource.Error -> {
                        _state.value = ApodState(error = "Error")
                    }
                }
            }.launchIn(viewModelScope)
    }
}