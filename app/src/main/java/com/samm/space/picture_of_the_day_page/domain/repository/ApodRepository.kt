package com.samm.space.picture_of_the_day_page.domain.repository

import com.samm.space.core.Resource
import com.samm.space.picture_of_the_day_page.domain.models.Apod
import kotlinx.coroutines.flow.Flow

interface ApodRepository {
    suspend fun getData(): Apod?
    fun getApodData(): Flow<Resource<Apod?>>

}