package com.test.weather.domain.viewmodel

import com.test.weather.domain.model.WeatherData

sealed class WeatherState {
    object Empty : WeatherState()
    data class Loaded(val weather: WeatherData, val query: String) : WeatherState()
    data class SearchResults(val results: List<String>, val query: String) : WeatherState()
    data class NoResults(val query: String) : WeatherState()
}