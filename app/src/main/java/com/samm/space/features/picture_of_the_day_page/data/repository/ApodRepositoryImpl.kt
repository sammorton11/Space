package com.samm.space.features.picture_of_the_day_page.data.repository

import com.samm.space.core.Resource
import com.samm.space.features.favorites_page.data.database.SpaceExplorerDatabase
import com.samm.space.features.picture_of_the_day_page.data.network.ApodApi
import com.samm.space.features.picture_of_the_day_page.domain.models.Apod
import com.samm.space.features.picture_of_the_day_page.domain.repository.ApodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApodRepositoryImpl @Inject constructor(
    private val api: ApodApi, database: SpaceExplorerDatabase
): ApodRepository {

    private val dao = database.myDao()

    override suspend fun getData(): Apod? {
        return api.getApod()
    }

    override fun getApodData() = flow {
        emit(Resource.Loading())
        val response = getData()
        emit(Resource.Success(response))
    }.catch { e ->
        emit(Resource.Error(e.toString()))
    }

    override fun getAllFavorites(): Flow<List<Apod>> {
        return dao.getAllApodFavorites()
    }

    override suspend fun insertFavorite(item: Apod) {
        dao.insertFavorite(item)
    }

    override suspend fun deleteFavorite(item: Apod) {
        dao.deleteFavorite(item)
    }
}