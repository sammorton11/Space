package com.samm.space.pages.favorites_page.di

import android.app.Application
import androidx.room.Room
import com.samm.space.pages.favorites_page.data.FavoritesRepositoryImpl
import com.samm.space.pages.favorites_page.data.database.SpaceExplorerDatabase
import com.samm.space.pages.favorites_page.domain.FavoritesRepository
import com.samm.space.pages.nasa_media_library_page.data.network.MetadataApi
import com.samm.space.pages.nasa_media_library_page.data.network.NasaApi
import com.samm.space.pages.nasa_media_library_page.data.repository.MediaLibraryRepositoryImpl
import com.samm.space.pages.nasa_media_library_page.domain.repository.MediaLibraryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMyDatabase(application: Application): SpaceExplorerDatabase {
        return Room.databaseBuilder(
            application,
            SpaceExplorerDatabase::class.java, "space_explorer_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoritesRepository(
        database: SpaceExplorerDatabase
    ): FavoritesRepository {

        return FavoritesRepositoryImpl(database)
    }
}