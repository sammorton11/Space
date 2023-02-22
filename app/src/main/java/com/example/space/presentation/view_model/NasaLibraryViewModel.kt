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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NasaLibraryViewModel @Inject constructor (private val repository: RepositoryImpl): ViewModel() {

    private val _state = mutableStateOf(NasaLibraryState()) // not exposed because mutable
    val state: State<NasaLibraryState> = _state // expose this to composable because immutable

    private fun searchImageVideoLibrary(query: String) = flow {

        try {
            emit(Resource.Loading())
            val response = repository.getData(query)
            val rawJson = response.body()?.toString() ?: ""
            Log.d("RAW JSON RESPONSE", rawJson)
            emit(Resource.Success(response))

        } catch (e: Exception) {
            emit(Resource.Error(e.toString()))
        }
    }

    fun getData(query: String) {
        searchImageVideoLibrary(query).onEach { response ->

            when(response) {
                is Resource.Success -> {
                    _state.value = NasaLibraryState(
                        data = response.data?.body()?.collection?.items ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _state.value = NasaLibraryState(
                        error = response.data?.errorBody().toString()
                    )
                    Log.d("ITEM ERROR", "${response.message}")
                }
                is Resource.Loading -> {
                    _state.value = NasaLibraryState(isLoading = true)
                    Log.d("ITEM LOADING", "true")
                }
            }
        }.launchIn(viewModelScope)
    }

//    fun getVideoData(url: String): Response<String> {
//        val metadata = repository.getVideoData(url)
////        for (element in metadata) {
////            Log.d("VIDEO DATA", element)
////        }
//        return metadata
//    }
}