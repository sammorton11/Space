package com.example.space.nasa_media_library.data.repository

import android.util.Log
import com.example.space.nasa_media_library.data.network.MetadataApi
import com.example.space.nasa_media_library.data.network.NasaApi
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.NasaLibraryResponse
import com.example.space.nasa_media_library.domain.repository.MediaLibraryRepository
import retrofit2.Response
import javax.inject.Inject

class MediaLibraryRepositoryImpl @Inject constructor(
    private val api: NasaApi,
    private val apiMetaData: MetadataApi
    ): MediaLibraryRepository {
    override suspend fun getData(query: String): Response<NasaLibraryResponse> {
        Log.d("Repo response:", "${api.fetchData(query)}")
        return api.fetchData(query)
    }

    override suspend fun getVideoData(url: String): Response<String> {
        return apiMetaData.fetchData(url)
    }
}