package com.example.space.di

import com.example.space.core.Constants.BASE_URL
import com.example.space.core.Constants.BASE_URL_MARS_DATA
import com.example.space.mars_weather.data.MarsWeatherApi
import com.example.space.nasa_media_library.data.network.MetadataApi
import com.example.space.nasa_media_library.data.network.NasaApi
import com.example.space.nasa_media_library.data.repository.MediaLibraryRepositoryImpl
import com.example.space.nasa_media_library.domain.repository.MediaLibraryRepository
import com.example.space.picture_of_the_day.data.ApodApi
import com.example.space.picture_of_the_day.data.repository.ApodRepositoryImpl
import com.example.space.picture_of_the_day.domain.repository.ApodRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNasaApi(): NasaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMarsWeatherApi(): MarsWeatherApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_MARS_DATA)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarsWeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMetaApi(): MetadataApi {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
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