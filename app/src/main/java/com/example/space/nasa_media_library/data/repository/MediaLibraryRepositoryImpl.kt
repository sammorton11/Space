package com.example.space.nasa_media_library.data.repository

import android.util.Log
import com.example.space.core.DataStoreManager
import com.example.space.core.Resource
import com.example.space.nasa_media_library.data.network.MetadataApi
import com.example.space.nasa_media_library.data.network.NasaApi
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.NasaLibraryResponse
import com.example.space.nasa_media_library.domain.repository.MediaLibraryRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class MediaLibraryRepositoryImpl @Inject constructor(
        private val api: NasaApi,
        private val apiMetaData: MetadataApi
    ): MediaLibraryRepository {

    private val dataStore = DataStoreManager
    override suspend fun getData(query: String): Response<NasaLibraryResponse> {
        Log.d("Repo response:", "${api.fetchData(query)}")
        return api.fetchData(query)
    }
    override suspend fun getVideoData(url: String): Response<String> {
        return apiMetaData.fetchData(url)
    }
    override fun searchImageVideoLibrary(query: String) = flow {
        emit(Resource.Loading())
        val response = getData(query)
        val errorString = response.errorBody()?.string()
        if (errorString?.isNotEmpty() == true) {
            emit(Resource.Error(errorString))
        } else {
            emit(Resource.Success(response))
        }
    }.catch { error ->
        emit(Resource.Error(error.toString()))
    }

    override fun savedQueryFlow() = flow {
        emit(dataStore.getLastSearchText())
    }.catch { error ->
        Log.d("Error getting Saved Query", error.toString())
    }
}