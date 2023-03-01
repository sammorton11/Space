package com.example.space.presentation.nasa_media_library.view_models

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.space.core.Resource
import com.example.space.domain.repository.MediaLibraryRepository
import com.example.space.presentation.nasa_media_library.state.NasaLibraryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NasaLibraryViewModel @Inject constructor (private val mediaLibraryRepository: MediaLibraryRepository): ViewModel() {

    private val _state = mutableStateOf(NasaLibraryState())
    val state: State<NasaLibraryState> = _state

    private fun searchImageVideoLibrary(query: String) = flow {
        emit(Resource.Loading())
        val response = mediaLibraryRepository.getData(query)
        emit(Resource.Success(response))
    }.catch { error ->
        emit(Resource.Error(error.toString()))
    }

    fun getData(query: String) {
        searchImageVideoLibrary(query).onEach { response ->
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
}