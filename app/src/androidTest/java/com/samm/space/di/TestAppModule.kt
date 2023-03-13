package com.samm.space.di

import com.google.gson.GsonBuilder
import com.samm.space.core.Constants
import com.samm.space.fakes.FakeApodRepository
import com.samm.space.fakes.FakeMediaLibraryRepository
import com.samm.space.nasa_media_library.data.network.MetadataApi
import com.samm.space.nasa_media_library.data.network.NasaApi
import com.samm.space.nasa_media_library.domain.repository.MediaLibraryRepository
import com.samm.space.picture_of_the_day.domain.repository.ApodRepository
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

    // Api providers are not actually called -- Hilt throws errors without them
    @Provides
    @Singleton
    fun provideRepository(): MediaLibraryRepository {
        return FakeMediaLibraryRepository()
    }

    @Provides
    @Singleton
    fun provideApodRepositorySuccess(): ApodRepository {
        return FakeApodRepository()
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
}