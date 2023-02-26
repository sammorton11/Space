package com.example.space.presentation.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.space.core.Resource
import com.example.space.data.repository.RepositoryImpl
import com.example.space.presentation.NasaLibraryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NasaLibraryViewModel @Inject constructor (private val repository: RepositoryImpl): ViewModel() {

    private val _state = mutableStateOf(NasaLibraryState()) // not exposed because mutable
    val state: State<NasaLibraryState> = _state // expose this to composable because immutable

    private fun searchImageVideoLibrary(query: String) = flow {
        emit(Resource.Loading())
        val response = repository.getData(query)
        val rawJson = response.body()?.toString() ?: ""
        Log.d("RAW JSON RESPONSE", rawJson)
        emit(Resource.Success(response))

    }.catch { error ->
        emit(Resource.Error(error.toString()))
    }

    fun getData(query: String) {
        searchImageVideoLibrary(query).onEach { response ->
            val responseData = response.data
            val responseBody = responseData?.body()
            when(response) {
                is Resource.Success -> {
                    _state.value = NasaLibraryState(
                        data = responseBody?.collection?.items ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _state.value = NasaLibraryState(
                        error = responseData?.errorBody().toString()
                    )
                }
                is Resource.Loading -> {
                    _state.value = NasaLibraryState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}