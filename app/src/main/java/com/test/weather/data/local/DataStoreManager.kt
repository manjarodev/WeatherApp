package com.test.weather.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    // Key for storing the city name
    private val CITY_KEY = stringPreferencesKey("selected_city")

    // Save city to DataStore
    suspend fun saveCity(city: String) {
        dataStore.edit { preferences ->
            preferences[CITY_KEY] = city
        }
    }

    // Get the city as a Flow (for observing changes)
    val selectedCity: Flow<String?> = dataStore.data.map { preferences ->
        preferences[CITY_KEY]
    }

    // Directly retrieve the city value as a suspend function
    suspend fun getCity(): String? {
        return dataStore.data.map { preferences ->
            preferences[CITY_KEY]
        }.first()
    }
}