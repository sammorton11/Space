package com.example.space.nasa_media_library.presentation.view_models

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.space.core.DataStoreManager
import com.example.space.core.Resource
import com.example.space.nasa_media_library.domain.repository.MediaLibraryRepository
import com.example.space.nasa_media_library.presentation.state.NasaLibraryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MediaLibraryViewModel @Inject constructor
    (private val mediaLibraryRepository: MediaLibraryRepository): ViewModel() {

    private val _state = mutableStateOf(NasaLibraryState())
    val state: State<NasaLibraryState> = _state

    fun getData(query: String) {
        mediaLibraryRepository.searchImageVideoLibrary(query).onEach { response ->

            DataStoreManager.saveLastSearchText(query)
            val success = response.data?.body()
            val error = response.message
            val itemsList = success?.collection?.items

            when(response) {
                is Resource.Success -> {
                    _state.value = NasaLibraryState(data = itemsList ?: emptyList())
                }
                is Resource.Error -> {
                    error?.let {
                        Log.d("Error message - ViewModel", it)
                        _state.value = NasaLibraryState(error = "Error! $it")
                    }
                }
                is Resource.Loading -> {
                    _state.value = NasaLibraryState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSavedSearchText(): String {
        var result = ""
        mediaLibraryRepository.savedQueryFlow().onEach { query ->
            query?.let { savedQuery ->
                result = savedQuery
            }
        }.onCompletion {
            if (result.isEmpty()) {
                result = "Search..."
            }
        }.launchIn(viewModelScope)

        return result
    }
}