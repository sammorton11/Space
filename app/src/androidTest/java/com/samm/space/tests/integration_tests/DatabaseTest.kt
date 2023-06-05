package com.samm.space.tests.integration_tests

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.platform.app.InstrumentationRegistry
import com.samm.space.fakes.FakeMetadataApi
import com.samm.space.fakes.FakeNasaApi
import com.samm.space.pages.favorites_page.data.database.SpaceExplorerDatabase
import com.samm.space.pages.nasa_media_library_page.domain.repository.MediaLibraryRepository
import com.samm.space.repository.FakeMediaLibraryRepositoryMock
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class DatabaseTest {

    private lateinit var instrumentationContext: Context
    private lateinit var repository: MediaLibraryRepository
    private val nasaApi = FakeNasaApi()
    private val metaDataApi = FakeMetadataApi()
    private lateinit var database: RoomDatabase

    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        database = Room.inMemoryDatabaseBuilder(instrumentationContext, SpaceExplorerDatabase::class.java)
            .build()
        repository = FakeMediaLibraryRepositoryMock(
            api = nasaApi,
            apiMetaData = metaDataApi,
            database = database as SpaceExplorerDatabase
        )
    }

    @Test
    fun test_insert_favorite(): Unit = runBlocking {
        val item = repository.getData("")?.collection?.items?.first()
        item?.let { repository.insertFavorite(it) }
        val favorites = repository.getAllFavorites()
        val listFromFavoritesFlow = favorites.first()

        assert(listFromFavoritesFlow.isNotEmpty())
    }
}