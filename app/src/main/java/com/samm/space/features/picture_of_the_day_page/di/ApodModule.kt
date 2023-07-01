package com.samm.space.features.picture_of_the_day_page.di

import com.samm.space.core.Constants.BASE_URL_APOD
import com.samm.space.features.favorites_page.data.database.SpaceExplorerDatabase
import com.samm.space.features.picture_of_the_day_page.data.network.ApodApi
import com.samm.space.features.picture_of_the_day_page.data.repository.ApodRepositoryImpl
import com.samm.space.features.picture_of_the_day_page.domain.repository.ApodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApodModule {

    @Provides
    @Singleton
    fun provideApodApi(): ApodApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_APOD)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApodApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApodRepository(api: ApodApi, database: SpaceExplorerDatabase): ApodRepository {
        return ApodRepositoryImpl(api, database)
    }
}