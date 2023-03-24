package com.samm.space.di

import com.google.gson.GsonBuilder
import com.samm.space.core.Constants.BASE_URL
import com.samm.space.core.Constants.BASE_URL_MARS_DATA
import com.samm.space.nasa_media_library.data.network.MetadataApi
import com.samm.space.nasa_media_library.data.network.NasaApi
import com.samm.space.nasa_media_library.data.repository.MediaLibraryRepositoryImpl
import com.samm.space.nasa_media_library.domain.repository.MediaLibraryRepository
import com.samm.space.picture_of_the_day.data.ApodApi
import com.samm.space.picture_of_the_day.data.repository.ApodRepositoryImpl
import com.samm.space.picture_of_the_day.domain.repository.ApodRepository
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
open class AppModule {

    open fun baseUrl() = BASE_URL.toHttpUrl()

    @Provides
    @Singleton
    open fun provideNasaApi(): NasaApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaApi::class.java)
    }

    @Provides
    @Singleton
    open fun provideMetaApi(): MetadataApi {
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
    fun provideRepository(api: NasaApi, metadataApi: MetadataApi): MediaLibraryRepository {
        return MediaLibraryRepositoryImpl(api, metadataApi)
    }

    @Provides
    @Singleton
    fun provideApodApi(): ApodApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_MARS_DATA)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApodApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApodRepository(api: ApodApi): ApodRepository {
        return ApodRepositoryImpl(api)
    }
}