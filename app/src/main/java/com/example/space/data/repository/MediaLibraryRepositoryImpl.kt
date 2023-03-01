package com.example.space.data.repository

import android.util.Log
import com.example.space.data.network.MetadataApi
import com.example.space.data.network.NasaApi
import com.example.space.domain.models.NasaLibraryResponse
import com.example.space.domain.repository.MediaLibraryRepository
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