package com.samm.space.picture_of_the_day.data.repository

import com.samm.space.picture_of_the_day.data.ApodApi
import com.samm.space.picture_of_the_day.domain.models.Apod
import com.samm.space.picture_of_the_day.domain.repository.ApodRepository
import retrofit2.Response
import javax.inject.Inject

class ApodRepositoryImpl @Inject constructor(private val api: ApodApi): ApodRepository {
    override suspend fun getData(): Response<Apod> {
        return api.getApod()
    }
}