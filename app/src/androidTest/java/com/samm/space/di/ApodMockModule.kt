package com.samm.space.di

import com.samm.space.picture_of_the_day_page.data.ApodApi
import com.samm.space.picture_of_the_day_page.di.ApodModule
import com.samm.space.picture_of_the_day_page.domain.repository.ApodRepository
import com.samm.space.repository.FakeApodRepositoryMock
import com.samm.space.test.BuildConfig
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

    private fun baseUrl(): HttpUrl = runBlocking(Dispatchers.Default) {
        serverApod.url("/")
    }

    @Provides
    @Singleton
    fun provideMockApodApi(): ApodApi {

        val clientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            clientBuilder.addNetworkInterceptor(HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY
            ))
        }

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