package com.test.weather.data.repository

import com.test.weather.data.api.WeatherApiService
import com.test.weather.data.local.DataStoreManager
import com.test.weather.data.model.WeatherResponse
import com.test.weather.domain.repository.WeatherRepository
import com.test.weather.utils.Constants.API_KEY
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService,
    private val dataStoreManager: DataStoreManager
) : WeatherRepository {
    override suspend fun getWeather(city: String): WeatherResponse {
        return apiService.getCurrentWeather(API_KEY, city).body()!!
    }

    override suspend fun saveSelectedCity(city: String) {
        dataStoreManager.saveCity(city)
    }

    override suspend fun getSelectedCity(): String? {
        return dataStoreManager.getCity()
    }
}