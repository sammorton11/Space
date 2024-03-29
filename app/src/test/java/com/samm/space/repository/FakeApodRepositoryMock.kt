package com.samm.space.repository

import android.util.Log
import com.samm.space.core.Resource
import com.samm.space.features.picture_of_the_day_page.data.network.ApodApi
import com.samm.space.features.picture_of_the_day_page.domain.models.Apod
import com.samm.space.features.picture_of_the_day_page.domain.repository.ApodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FakeApodRepositoryMock @Inject constructor (private val api: ApodApi): ApodRepository {

    override suspend fun getData(): Apod? {
        return api.getApod()
    }

    override suspend fun getDataByDate(date: String): Apod? {
        TODO("Not yet implemented")
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
        }
    }

    override fun getAllFavorites(): Flow<List<Apod>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFavorite(item: Apod) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavorite(item: Apod) {
        TODO("Not yet implemented")
    }
}