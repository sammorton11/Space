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
class MediaLibraryViewModel
@Inject constructor (private val mediaLibraryRepository: MediaLibraryRepository): ViewModel() {

    private val _state = mutableStateOf(NasaLibraryState())
    val state: State<NasaLibraryState> = _state

    private val dataStore = DataStoreManager

    private fun searchImageVideoLibrary(query: String) = flow {
        emit(Resource.Loading())
        val response = mediaLibraryRepository.getData(query)
        val errorString = response.errorBody()?.string()
        Log.d("response", response.body().toString())
        if (errorString?.isNotEmpty() == true) {
            emit(Resource.Error(errorString))
        } else {
            emit(Resource.Success(response))
        }

    }.catch { error ->
        emit(Resource.Error(error.toString()))
    }

    fun getData(query: String) {
        searchImageVideoLibrary(query).onEach { response ->

            DataStoreManager.saveLastSearchText(query)
            val success = response.data?.body()
            val error = response.data?.errorBody()
            val itemsList = success?.collection?.items

            when(response) {
                is Resource.Success -> {
                    _state.value = NasaLibraryState(data = itemsList ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = NasaLibraryState(error = error.toString())
                    Log.d("ITEM ERROR", "${response.message}")
                }
                is Resource.Loading -> {
                    _state.value = NasaLibraryState(isLoading = true)
                    Log.d("ITEM LOADING", "true")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun savedQueryFlow() = flow {
        emit(dataStore.getLastSearchText())
    }.catch { error -> Log.d("Error getting Saved Query", error.toString()) }

    fun getSavedSearchText(): String {
        var result = ""
        savedQueryFlow().onEach { query ->
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