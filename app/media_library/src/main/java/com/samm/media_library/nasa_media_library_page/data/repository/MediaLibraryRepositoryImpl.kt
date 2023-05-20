package com.samm.media_library.nasa_media_library_page.data.repository


import android.util.Log
import com.samm.core.common.data.DataStoreManager
import com.samm.core.data.database.SpaceExplorerDatabase
import com.samm.core.domain.library_models.Item
import com.samm.core.domain.library_models.NasaLibraryResponse
import com.samm.core.util.Resource
import com.samm.media_library.nasa_media_library_page.data.network.MetadataApi
import com.samm.media_library.nasa_media_library_page.data.network.NasaApi
import com.samm.media_library.nasa_media_library_page.domain.repository.MediaLibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
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

        try {
            emit(Resource.Loading())
            val response = getData(query)
            Log.d("Response", response.toString())
            emit(Resource.Success(response))
        }
        catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        }
        catch (e: IOException){
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        }
    }

    override fun videoDataFlow(url: String) = flow {
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