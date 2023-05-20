package com.samm.space.di

import com.samm.space.fakes.FakeMetadataApi
import com.samm.space.fakes.FakeNasaApi
import com.samm.core.data.database.SpaceExplorerDatabase
import com.samm.media_library.nasa_media_library_page.data.network.MetadataApi
import com.samm.media_library.nasa_media_library_page.data.network.NasaApi
import com.samm.media_library.nasa_media_library_page.di.MediaLibraryModule
import com.samm.media_library.nasa_media_library_page.domain.repository.MediaLibraryRepository
import com.samm.space.repository.FakeMediaLibraryRepositoryMock
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [MediaLibraryModule::class]
)
@Module
object MediaLibraryMockModule {

    @Provides
    @Singleton
    fun provideMockNasaApi(): NasaApi {
        return FakeNasaApi()
    }


    @Provides
    @Singleton
    fun provideMetaMockApi(): MetadataApi {
        return FakeMetadataApi()
    }


    @Provides
    @Singleton
    fun provideMediaLibraryRepositoryMock(
        api: NasaApi,
        metadataApi: MetadataApi,
        database: SpaceExplorerDatabase
    ): MediaLibraryRepository {

        return FakeMediaLibraryRepositoryMock(api, metadataApi, database)
    }
}