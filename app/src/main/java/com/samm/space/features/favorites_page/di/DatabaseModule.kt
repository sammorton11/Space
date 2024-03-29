package com.samm.space.features.favorites_page.di

import android.app.Application
import androidx.room.Room
import com.samm.space.features.favorites_page.data.database.SpaceExplorerDatabase
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

        )
            .fallbackToDestructiveMigration()
            .build()
    }
}