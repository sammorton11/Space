package com.samm.space.nasa_media_library.data.repository

import android.util.Log
import com.samm.space.core.DataStoreManager
import com.samm.space.core.Resource
import com.samm.space.nasa_media_library.data.network.MetadataApi
import com.samm.space.nasa_media_library.data.network.NasaApi
import com.samm.space.nasa_media_library.domain.models.NasaLibraryResponse
import com.samm.space.nasa_media_library.domain.repository.MediaLibraryRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MediaLibraryRepositoryImpl @Inject constructor(
        private val api: NasaApi,
        private val apiMetaData: MetadataApi
    ): MediaLibraryRepository {

    private val dataStore = DataStoreManager // should I inject this?
    override suspend fun getData(query: String): NasaLibraryResponse? {
        return api.fetchData(query)
    }
    override suspend fun getVideoData(url: String): String? {
        return apiMetaData.fetchData(url)
    }
    override fun searchImageVideoLibrary(query: String) = flow {
        try {
            emit(Resource.Loading())
            val response = getData(query)
            emit(Resource.Success(response))
        }
        catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        }
        catch (e: IOException){
            emit(Resource.Error("Check Internet Connection"))
        }
    }

    override fun videoDataFlow(url: String) = flow {
        try {
            emit(Resource.Loading())
            val response = getVideoData(url)
            emit(Resource.Success(response))
        }
        catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        }
        catch (e: IOException){
            emit(Resource.Error("Check Internet Connection"))
        }
    }

    override fun savedQueryFlow() = flow {
        emit(dataStore.getLastSearchText())
    }.catch { error ->
        Log.d("Error getting Saved Query", error.toString())
    }
}