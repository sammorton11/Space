package com.example.space.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object DataStoreManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val LAST_SEARCH_TEXT_KEY =  stringPreferencesKey("last_search_text")
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var preferenceFlow: Flow<String>

    fun init(context: Context) {
        dataStore = context.dataStore
        preferenceFlow = context.dataStore.data.map {
            it[LAST_SEARCH_TEXT_KEY] ?: ""
        }
    }

    // Save and Get search text within the search field on the library screen
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
