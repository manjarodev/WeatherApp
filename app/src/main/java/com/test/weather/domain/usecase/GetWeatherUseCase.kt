package com.test.weather.domain.usecase

import com.test.weather.data.model.WeatherResponse
import com.test.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String): WeatherResponse {
        return repository.getWeather(city)
    }
}