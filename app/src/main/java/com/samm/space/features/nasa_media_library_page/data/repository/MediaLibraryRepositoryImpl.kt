package com.samm.space.features.nasa_media_library_page.data.repository

import android.util.Log
import com.samm.space.core.DataStoreManager
import com.samm.space.core.Resource
import com.samm.space.features.favorites_page.data.database.SpaceExplorerDatabase
import com.samm.space.features.nasa_media_library_page.data.network.MetadataApi
import com.samm.space.features.nasa_media_library_page.data.network.NasaApi
import com.samm.space.features.nasa_media_library_page.domain.models.Item
import com.samm.space.features.nasa_media_library_page.domain.models.NasaLibraryResponse
import com.samm.space.features.nasa_media_library_page.domain.repository.MediaLibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MediaLibraryRepositoryImpl @Inject constructor(
    private val api: NasaApi,
    private val apiMetaData: MetadataApi,
    private val database: SpaceExplorerDatabase
): MediaLibraryRepository {

    private val dataStore = DataStoreManager

    override suspend fun getData(query: String): NasaLibraryResponse? {
        return api.fetchData(query)
    }

    override suspend fun getVideoData(url: String): String? {
        return apiMetaData.fetchData(url)
    }

    override fun searchImageVideoLibrary(query: String) = flow {
        DataStoreManager.saveLastSearchText(query)
        emit(Resource.Loading())
        val response = getData(query)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "Unexpected Error"))
    }

    override fun mediaDataFlow(url: String) = flow {
        try {
            emit(Resource.Loading())
            val response = getVideoData(url)
            emit(Resource.Success(response))
        }
        catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getAllFavorites(): Flow<List<Item>> {
        return database.myDao().getAllLibraryFavorites()
    }

    override suspend fun insertFavorite(item: Item) {
        database.myDao().insertFavorite(item)
    }

    override suspend fun deleteFavorite(item: Item) {
        database.myDao().deleteFavorite(item)
    }

    override suspend fun updateFavorite(itemId: Int, isFavorite: Boolean) {
        database.myDao().updateFavoriteState(itemId, if (isFavorite) 1 else 0)
    }

    override fun savedQueryFlow() = flow {
        emit(dataStore.getLastSearchText())
    }.catch { error ->
        Log.d("Error getting Saved Query", error.toString())
    }
}