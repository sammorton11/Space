package com.samm.space.pages.picture_of_the_day_page.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samm.space.core.Resource
import com.samm.space.pages.picture_of_the_day_page.domain.repository.ApodRepository
import com.samm.space.pages.picture_of_the_day_page.presentation.state.ApodState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ApodViewModel @Inject constructor(private val repository: ApodRepository): ViewModel() {

    private val _state = MutableStateFlow(ApodState())
    val state: StateFlow<ApodState> = _state
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
                    _state.value = ApodState(data = response.data)
                }
                is Resource.Error -> {
                    _state.value = ApodState(error = "Error: ${response.message.toString()}")
                }
            }
        }.launchIn(viewModelScope)
    }
}