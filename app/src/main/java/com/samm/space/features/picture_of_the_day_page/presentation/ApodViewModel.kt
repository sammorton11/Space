package com.samm.space.features.picture_of_the_day_page.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samm.space.core.Resource
import com.samm.space.features.favorites_page.presentation.state.ApodFavoriteState
import com.samm.space.features.picture_of_the_day_page.domain.models.Apod
import com.samm.space.features.picture_of_the_day_page.domain.repository.ApodRepository
import com.samm.space.features.picture_of_the_day_page.presentation.state.ApodState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApodViewModel @Inject constructor(private val repository: ApodRepository): ViewModel() {

    private val _state = MutableStateFlow(ApodState())
    val state: StateFlow<ApodState> = _state

    private val _favoriteState = MutableStateFlow(ApodFavoriteState())
    var favoriteState: StateFlow<ApodFavoriteState> = _favoriteState

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

    fun getApodFavorites() = viewModelScope.launch(Dispatchers.IO) {
        val favorites = repository.getAllFavorites()

        favorites.collect {
            _favoriteState.value.apodFavorites = it
        }
    }

    private fun getDataDateFlow(date: String) = flow {
        emit(Resource.Loading())
        val response = repository.getDataByDate(date)
        emit(Resource.Success(response))
    }.catch { e ->
        emit(Resource.Error(e.message ?: "Unknown Error"))
    }

    fun getDataByDate(date: String) {
        getDataDateFlow(date).onEach { response ->
            when (response) {
                is Resource.Loading -> {
                    _state.value = ApodState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = ApodState(data = response.data)
                }
                is Resource.Error -> {
                    _state.value = ApodState(error = response.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun insert(item: Apod) = viewModelScope.launch(Dispatchers.IO) {
        val list = favoriteState.value.apodFavorites
        val isItemAlreadyExists = list?.any { it.hdurl == item.hdurl }

        if (isItemAlreadyExists == false) {
            repository.insertFavorite(item)
        }
    }

    fun delete(item: Apod) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteFavorite(item)
    }

    fun encodeText(text: String?): String {
        val encodedText = text?.let {
            Uri.encode(it)
        } ?: "Not Available"

        return Uri.Builder()
            .encodedPath(encodedText)
            .build()
            .toString()
    }
}