package com.example.space.data.repository

import com.example.space.data.network.MetadataApi
import com.example.space.data.network.NasaApi
import com.example.space.domain.models.Collection
import com.example.space.domain.models.Metadata
import com.example.space.domain.models.NasaLibraryResponse
import com.example.space.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: NasaApi,
    private val apiMetaData: MetadataApi
    ): Repository {
    override suspend fun getData(query: String): Response<NasaLibraryResponse> {
        return api.fetchData(query)
    }

    override suspend fun getVideoData(url: String): Response<String> {
        return apiMetaData.fetchData(url)
    }
}