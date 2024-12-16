package com.test.weather.data.model

data class WeatherResponse(
    val location: Location,
    val current: Current
)

data class Location(val name: String, val region: String)
data class Current(
    val tempC: Float,
    val condition: Condition,
    val humidity: Int,
    val uv: Float,
    val feelslikeC: Float
)

data class Condition(val text: String, val icon: String)