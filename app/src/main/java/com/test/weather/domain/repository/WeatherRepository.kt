package com.test.weather.domain.repository

import com.test.weather.data.model.WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(city: String): WeatherResponse
    suspend fun saveSelectedCity(city: String)
    suspend fun getSelectedCity(): String?
}