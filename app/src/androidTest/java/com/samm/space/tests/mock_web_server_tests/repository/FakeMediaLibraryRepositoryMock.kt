package com.samm.space.tests.mock_web_server_tests.repository

import android.util.Log
import com.samm.space.core.Resource
import com.samm.space.nasa_media_library.data.network.MetadataApi
import com.samm.space.nasa_media_library.data.network.NasaApi
import com.samm.space.nasa_media_library.domain.models.*
import com.samm.space.nasa_media_library.domain.models.Collection
import com.samm.space.nasa_media_library.domain.repository.MediaLibraryRepository
import com.samm.space.picture_of_the_day.data.ApodApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FakeMediaLibraryRepositoryMock @Inject constructor (
    private val api: NasaApi,
    private val apiMetaData: MetadataApi
): MediaLibraryRepository {

    override suspend fun getData(query: String): NasaLibraryResponse? {
        Log.d("I love maty:", api.fetchData(query).toString())
        return api.fetchData(query)
    }

    override suspend fun getVideoData(url: String): String? {
        return apiMetaData.fetchData(url)
    }

    override fun searchImageVideoLibrary(query: String) = flow {
        try {
            emit(Resource.Loading())
            val response = getData(query)
            Log.d("response mock:", response.toString())
            emit(Resource.Success(response))
        }
        catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        }
        catch (e: IOException){
            emit(Resource.Error(e.localizedMessage ?: "Check Internet Connection"))
        }
    }

    override fun savedQueryFlow(): Flow<String?> = flow {
        emit("Fake saved query")
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
}