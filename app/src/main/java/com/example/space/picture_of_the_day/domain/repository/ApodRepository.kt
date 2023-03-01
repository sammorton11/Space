package com.example.space.picture_of_the_day.domain.repository

import com.example.space.picture_of_the_day.domain.models.Apod
import retrofit2.Response

interface ApodRepository {
    suspend fun getData(): Response<Apod>
}