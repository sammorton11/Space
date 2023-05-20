package com.samm.core.common.data

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
    private val LAST_SEARCH_TEXT_KEY = stringPreferencesKey("last_search_text")
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var preferenceFlow: Flow<String>

    fun init(context: Context) {
        com.samm.core.common.data.DataStoreManager.dataStore = context.dataStore
        com.samm.core.common.data.DataStoreManager.preferenceFlow = context.dataStore.data.map {
            it[com.samm.core.common.data.DataStoreManager.LAST_SEARCH_TEXT_KEY] ?: ""
        }
    }

    // Save and Get search text within the search field on the library screen
    suspend fun saveLastSearchText(lastSearchText: String) {
        com.samm.core.common.data.DataStoreManager.dataStore.edit { preferences ->
            preferences[com.samm.core.common.data.DataStoreManager.LAST_SEARCH_TEXT_KEY] = lastSearchText
        }
    }

    suspend fun getLastSearchText(): String? {
        val preferences = com.samm.core.common.data.DataStoreManager.dataStore.data.first()
        return preferences[com.samm.core.common.data.DataStoreManager.LAST_SEARCH_TEXT_KEY]
    }
}
