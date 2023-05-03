package com.samm.space.pages.picture_of_the_day_page.data.repository

import android.util.Log
import com.samm.space.core.Resource
import com.samm.space.pages.picture_of_the_day_page.data.network.ApodApi
import com.samm.space.pages.picture_of_the_day_page.domain.models.Apod
import com.samm.space.pages.picture_of_the_day_page.domain.repository.ApodRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApodRepositoryImpl @Inject constructor(private val api: ApodApi): ApodRepository {
    override suspend fun getData(): Apod? {
        return api.getApod()
    }

    override fun getApodData() = flow {
        try {
            emit(Resource.Loading())
            val response = getData()
            Log.d("Response", response.toString())
            emit(Resource.Success(response))
        }
        catch (e: Exception) {
            emit(Resource.Error(e.toString()))
        }
    }
}