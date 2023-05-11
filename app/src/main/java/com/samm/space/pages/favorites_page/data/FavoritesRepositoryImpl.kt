package com.samm.space.pages.favorites_page.data

import com.samm.space.pages.favorites_page.data.database.SpaceExplorerDatabase
import com.samm.space.pages.favorites_page.domain.FavoritesRepository
import com.samm.space.pages.nasa_media_library_page.domain.models.Item
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepositoryImpl
@Inject constructor(private val database: SpaceExplorerDatabase): FavoritesRepository {

    override fun getAllLibraryFavorites(): Flow<List<Item>> {
        return database.myDao().getAllLibraryFavorites()
    }

    override suspend fun insertFavorite(item: Item) {
        database.myDao().insertFavorite(item)
    }

    override suspend fun deleteFavorite(item: Item) {
        database.myDao().deleteFavorite(item)
    }
}