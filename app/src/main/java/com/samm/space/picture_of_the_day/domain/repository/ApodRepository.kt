package com.samm.space.picture_of_the_day.domain.repository

import com.samm.space.picture_of_the_day.domain.models.Apod
import retrofit2.Response

interface ApodRepository {
    suspend fun getData(): Response<Apod>
}