package com.example.space.core

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.*
import com.example.space.core.DataStoreManager.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

object DataStoreManager {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val LAST_SEARCH_TEXT_KEY =  stringPreferencesKey("last_search_text")
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var preferenceFlow: Flow<String>

    fun init(context: Context) {
        dataStore = context.dataStore
        preferenceFlow = context.dataStore.data.map {
            it[LAST_SEARCH_TEXT_KEY] ?: ""
        }
    }

    suspend fun saveLastSearchText(lastSearchText: String) {
        dataStore.edit { preferences ->
            preferences[LAST_SEARCH_TEXT_KEY] = lastSearchText
        }
    }

    suspend fun getLastSearchText(): String? {
        val preferences = dataStore.data.first()
        return preferences[LAST_SEARCH_TEXT_KEY]
    }
}
