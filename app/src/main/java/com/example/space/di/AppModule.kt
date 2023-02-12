package com.example.space.di

import com.example.space.core.Constants.BASE_URL
import com.example.space.data.network.NasaApi
import com.example.space.data.repository.RepositoryImpl
import com.example.space.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApi(): NasaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: NasaApi): Repository {
        return RepositoryImpl(api)
    }
}