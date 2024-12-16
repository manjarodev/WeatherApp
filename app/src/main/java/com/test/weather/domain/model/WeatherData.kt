package com.test.weather.domain.model

data class WeatherData(
    val cityName: String,
    val temperature: String,
    val iconUrl: String,
    val humidity: String,
    val uvIndex: String,
    val feelsLike: String
)