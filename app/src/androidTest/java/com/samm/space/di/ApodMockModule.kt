package com.samm.space.di

import com.samm.space.pages.picture_of_the_day_page.data.network.ApodApi
import com.samm.space.pages.picture_of_the_day_page.di.ApodModule
import com.samm.space.pages.picture_of_the_day_page.domain.repository.ApodRepository
import com.samm.space.repository.FakeApodRepositoryMock
import com.samm.space.tests.ui_tests.ApodUITest.Companion.serverApod
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
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ApodModule::class]
)
@Module
object ApodMockModule {

    private val clientBuilder = OkHttpClient.Builder()

    private fun baseUrl(): HttpUrl = runBlocking(Dispatchers.Default) {
        serverApod.url("/")
    }

    private fun clientLogger() {

        // Logging the request and response when in debug mode
        if (com.samm.space.BuildConfig.DEBUG) {
            clientBuilder.addNetworkInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY
                ))
        }
    }

    @Provides
    @Singleton
    fun provideMockApodApi(): ApodApi {
        clientLogger()
        return Retrofit.Builder()
            .baseUrl(baseUrl())
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApodApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApodMockRepository(mockApi: ApodApi): ApodRepository {
        return FakeApodRepositoryMock(mockApi)
    }
}