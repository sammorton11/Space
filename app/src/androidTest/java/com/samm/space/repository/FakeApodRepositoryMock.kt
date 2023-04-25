package com.samm.space.repository

import android.util.Log
import com.samm.space.core.Resource
import com.samm.space.picture_of_the_day_page.data.network.ApodApi
import com.samm.space.picture_of_the_day_page.domain.models.Apod
import com.samm.space.picture_of_the_day_page.domain.repository.ApodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FakeApodRepositoryMock @Inject constructor (private val api: ApodApi): ApodRepository {

    override suspend fun getData(): Apod? {
        return api.getApod()
    }

    override fun getApodData(): Flow<Resource<Apod?>> = flow {
        try {
            emit(Resource.Loading())
            val response = getData()
            emit(Resource.Success(response))
            Log.d("response:", response.toString())
        }
        catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        }
        catch (e: IOException){
            emit(Resource.Error(e.toString()))
            Log.d("Error:", e.toString())
            println("Error: $e")
        }
    }
}