package com.samm.space.features.picture_of_the_day_page.domain.repository

import com.samm.space.core.Resource
import com.samm.space.features.picture_of_the_day_page.domain.models.Apod
import kotlinx.coroutines.flow.Flow

interface ApodRepository {

    suspend fun getData(): Apod?
    suspend fun getDataByDate(date: String): Apod?
    fun getApodData(): Flow<Resource<Apod?>>

    fun getAllFavorites(): Flow<List<Apod>>
    suspend fun insertFavorite(item: Apod)
    suspend fun deleteFavorite(item: Apod)
}