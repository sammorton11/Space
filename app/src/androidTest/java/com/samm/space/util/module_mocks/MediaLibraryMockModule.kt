package com.samm.space.util.module_mocks

import com.samm.space.nasa_media_library.data.network.MetadataApi
import com.samm.space.nasa_media_library.data.network.NasaApi
import com.samm.space.nasa_media_library.di.MediaLibraryModule
import com.samm.space.nasa_media_library.domain.repository.MediaLibraryRepository
import com.samm.space.repository.FakeMediaLibraryRepositoryMock
import com.samm.space.test.BuildConfig
import com.samm.space.tests.mock_web_server_tests.MediaLibraryUITest
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [MediaLibraryModule::class]
)
@Module
object MediaLibraryMockModule {

    private fun baseUrl(): HttpUrl = runBlocking(Dispatchers.Default) {
        MediaLibraryUITest.server.url("/")
    }

    @Provides
    @Singleton
    fun provideMockNasaApi(): NasaApi {
        val clientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            clientBuilder.addNetworkInterceptor(
                HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY
            ))
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaApi::class.java)
    }


    @Provides
    @Singleton
    fun provideMetaMockApi(): MetadataApi {

        val clientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            clientBuilder.addNetworkInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY
                ))
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MetadataApi::class.java)
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