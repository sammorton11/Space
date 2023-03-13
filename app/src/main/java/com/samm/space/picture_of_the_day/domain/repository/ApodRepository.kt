package com.samm.space.picture_of_the_day.domain.repository

import com.samm.space.core.Resource
import com.samm.space.picture_of_the_day.domain.models.Apod
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ApodRepository {
    suspend fun getData(): Response<Apod>
    fun getApodData(): Flow<Resource<Response<Apod>>>
}