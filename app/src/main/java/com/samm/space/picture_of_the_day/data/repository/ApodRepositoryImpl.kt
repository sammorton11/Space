package com.samm.space.picture_of_the_day.data.repository

import com.samm.space.core.Resource
import com.samm.space.picture_of_the_day.data.ApodApi
import com.samm.space.picture_of_the_day.domain.models.Apod
import com.samm.space.picture_of_the_day.domain.repository.ApodRepository
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class ApodRepositoryImpl @Inject constructor(private val api: ApodApi): ApodRepository {
    override suspend fun getData(): Response<Apod> {
        return api.getApod()
    }
    override fun getApodData() = flow {
        emit(Resource.Loading())
        val response = getData()
        emit(Resource.Error(response.errorBody()?.string()))
        emit(Resource.Success(response))
    }
}