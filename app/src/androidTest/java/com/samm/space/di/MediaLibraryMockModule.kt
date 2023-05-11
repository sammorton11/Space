package com.samm.space.di

import com.samm.space.BuildConfig
import com.samm.space.pages.nasa_media_library_page.data.network.MetadataApi
import com.samm.space.pages.nasa_media_library_page.data.network.NasaApi
import com.samm.space.pages.nasa_media_library_page.di.MediaLibraryModule
import com.samm.space.pages.nasa_media_library_page.domain.repository.MediaLibraryRepository
import com.samm.space.repository.FakeMediaLibraryRepositoryMock
import com.samm.space.tests.ui_tests.MediaLibraryUITest.Companion.serverMediaLibrary
import com.samm.space.tests.ui_tests.MediaLibraryUITest.Companion.serverMetadata
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

    private val clientBuilder = OkHttpClient.Builder()

    private fun baseUrl(): HttpUrl = runBlocking(Dispatchers.Default) {
        serverMediaLibrary.url("/")
    }

    private fun baseUrlMetadata(): HttpUrl = runBlocking(Dispatchers.Default) {
        serverMetadata.url("/")
    }

    private fun clientLogger() {

        // Logging the request and response when in debug mode
        if (BuildConfig.DEBUG) {
            clientBuilder.addNetworkInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY
                ))
        }
    }

    @Provides
    @Singleton
    fun provideMockNasaApi(): NasaApi {
        clientLogger()
        return Retrofit.Builder()
            .baseUrl(baseUrl())
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaApi::class.java)
    }


    @Provides
    @Singleton
    fun provideMetaMockApi(): MetadataApi {
        clientLogger()
        return Retrofit.Builder()
            .baseUrl(baseUrlMetadata())
            .client(clientBuilder.build())
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