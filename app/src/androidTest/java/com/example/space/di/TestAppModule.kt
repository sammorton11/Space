package com.example.space.di

import com.example.space.FakeRepository
import com.example.space.core.Constants
import com.example.space.data.network.MarsWeatherApi
import com.example.space.data.network.MetadataApi
import com.example.space.data.network.NasaApi
import com.example.space.domain.repository.Repository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
@Module
object TestAppModule {

    @Provides
    @Singleton
    fun provideRepository(): Repository {
        return FakeRepository()
    }

    @Provides
    @Singleton
    fun provideMetaApi(): MetadataApi {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MetadataApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNasaApi(): NasaApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMarsWeatherApi(): MarsWeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_MARS_DATA)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarsWeatherApi::class.java)
    }
}