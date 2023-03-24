package com.samm.space.tests.mock_web_server_tests

import com.google.gson.GsonBuilder
import com.samm.space.di.AppModule
import com.samm.space.fakes.FakeMediaLibraryRepository
import com.samm.space.nasa_media_library.data.network.MetadataApi
import com.samm.space.nasa_media_library.data.network.NasaApi
import com.samm.space.nasa_media_library.domain.repository.MediaLibraryRepository
import com.samm.space.picture_of_the_day.data.ApodApi
import com.samm.space.picture_of_the_day.domain.repository.ApodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
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

    private fun baseUrl(): HttpUrl {
        return MockServer.server.url("http://localhost/:8080/")
    }

    @Provides
    @Singleton
    fun provideMockApodApi(): ApodApi = runBlocking {
        withContext(Dispatchers.IO) {
            Retrofit.Builder()
                .baseUrl(MockServer.server.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApodApi::class.java)
        }
    }


    @Provides
    @Singleton
    fun provideMockRepository(mockApi: ApodApi): ApodRepository {
        return FakeApodRepositoryMock(mockApi)
    }

    @Provides
    @Singleton
    fun provideRepository(): MediaLibraryRepository {
        return FakeMediaLibraryRepository()
    }

//    @Provides
//    @Singleton
//    fun provideApodRepositorySuccess(): ApodRepository {
//        return FakeApodRepository()
//    }

    @Provides
    @Singleton
    fun provideMetaApi_(): MetadataApi {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(baseUrl())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MetadataApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNasaApi_(): NasaApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaApi::class.java)
    }
}