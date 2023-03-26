package com.samm.space.tests.mock_web_server_tests.util

import com.samm.space.di.AppModule
import com.samm.space.nasa_media_library.data.network.MetadataApi
import com.samm.space.nasa_media_library.data.network.NasaApi
import com.samm.space.nasa_media_library.domain.repository.MediaLibraryRepository
import com.samm.space.picture_of_the_day.data.ApodApi
import com.samm.space.picture_of_the_day.domain.repository.ApodRepository
import com.samm.space.tests.mock_web_server_tests.ApodUITest.Companion.server
import com.samm.space.tests.mock_web_server_tests.repository.FakeApodRepositoryMock
import com.samm.space.tests.mock_web_server_tests.repository.FakeMediaLibraryRepositoryMock
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
@Module
object MockModule {

    private fun baseUrl() = runBlocking(Dispatchers.IO) {
        server.url("/")
    }

    @Provides
    @Singleton
    fun provideMockApodApi(): ApodApi = runBlocking(Dispatchers.IO) {Retrofit.Builder()
        .baseUrl(server.url("/"))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApodApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMockNasaApi(): NasaApi = Retrofit.Builder()
        .baseUrl(baseUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NasaApi::class.java)


    @Provides
    @Singleton
    fun provideMetaApi_(): MetadataApi = Retrofit.Builder()
        .baseUrl(baseUrl())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MetadataApi::class.java)



    @Provides
    @Singleton
    fun provideApodMockRepository(mockApi: ApodApi): ApodRepository {
        return FakeApodRepositoryMock(mockApi)
    }

    @Provides
    @Singleton
    fun provideMediaLibraryRepositoryMock(
        api: NasaApi,
        metadataApi: MetadataApi
    ): MediaLibraryRepository {
        return FakeMediaLibraryRepositoryMock(api, metadataApi)
    }
}