package com.samm.space.picture_of_the_day

import com.samm.space.core.Constants.BASE_URL_MARS_DATA
import com.samm.space.picture_of_the_day.data.ApodApi
import com.samm.space.picture_of_the_day.data.repository.ApodRepositoryImpl
import com.samm.space.picture_of_the_day.domain.repository.ApodRepository
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