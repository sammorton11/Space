package com.samm.apod.picture_of_the_day_page.domain.repository

import com.samm.core.domain.apod_models.Apod
import com.samm.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface ApodRepository {
    suspend fun getData(): Apod?
    fun getApodData(): Flow<Resource<Apod?>>
}