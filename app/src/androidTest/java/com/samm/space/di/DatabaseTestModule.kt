package com.samm.space.di

import android.content.Context
import androidx.room.Room
import com.samm.space.features.favorites_page.data.database.SpaceExplorerDatabase
import com.samm.space.features.favorites_page.di.DatabaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
@Module
object DatabaseTestModule {

    @Provides
    @Singleton
    fun provideInMemoryRoomDatabase(
        @ApplicationContext context: Context
    ): SpaceExplorerDatabase {
        return Room.inMemoryDatabaseBuilder(context, SpaceExplorerDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}
