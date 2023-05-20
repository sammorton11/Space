package com.samm.media_library.nasa_media_library_page.di

import com.google.gson.GsonBuilder
import com.samm.core.data.database.SpaceExplorerDatabase
import com.samm.media_library.nasa_media_library_page.data.network.MetadataApi
import com.samm.media_library.nasa_media_library_page.data.network.NasaApi
import com.samm.media_library.nasa_media_library_page.data.repository.MediaLibraryRepositoryImpl
import com.samm.media_library.nasa_media_library_page.domain.repository.MediaLibraryRepository
import com.samm.shared_resources.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MediaLibraryModule {

     private fun baseUrl() = BASE_URL.toHttpUrl()

    @Provides
    @Singleton
     fun provideNasaApi(): NasaApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaApi::class.java)
    }

    @Provides
    @Singleton
     fun provideMetaApi(): MetadataApi {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(baseUrl())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MetadataApi::class.java)
     }

    @Provides
    @Singleton
    fun provideRepository(
        api: NasaApi,
        metadataApi: MetadataApi,
        database: SpaceExplorerDatabase,
    ): MediaLibraryRepository {

        return MediaLibraryRepositoryImpl(api, metadataApi, database)
    }
}