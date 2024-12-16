package com.test.weather.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.weather.domain.model.WeatherData
import com.test.weather.domain.repository.WeatherRepository
import com.test.weather.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val repository: WeatherRepository // For saving city data
) : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Empty)
    val weatherState: StateFlow<WeatherState> = _weatherState

    private var currentQuery: String = ""

    fun updateQuery(newQuery: String) {
        currentQuery = newQuery
    }

    fun searchCity() {
        viewModelScope.launch {
            if (currentQuery.isBlank()) {
                _weatherState.value = WeatherState.NoResults(currentQuery)
                return@launch
            }
            // Fetch weather data for the searched city
            try {
                val weatherResponse = getWeatherUseCase(currentQuery)
                repository.saveSelectedCity(currentQuery)
                _weatherState.value = WeatherState.Loaded(
                    weather = WeatherData(
                        cityName = weatherResponse.location.name,
                        temperature = "${weatherResponse.current.tempC}",
                        iconUrl = "https:${weatherResponse.current.condition.icon}",
                        humidity = "${weatherResponse.current.humidity}",
                        uvIndex = "${weatherResponse.current.uv}",
                        feelsLike = "${weatherResponse.current.feelslikeC}"
                    ),
                    query = currentQuery
                )
            } catch (e: Exception) {
                _weatherState.value = WeatherState.NoResults(currentQuery)
            }
        }
    }

    fun loadSavedCityWeather() {
        viewModelScope.launch {
            val savedCity = repository.getSelectedCity()
            if (!savedCity.isNullOrEmpty()) {
                val weatherResponse = getWeatherUseCase(savedCity)
                _weatherState.value = WeatherState.Loaded(
                    weather = WeatherData(
                        cityName = weatherResponse.location.name,
                        temperature = "${weatherResponse.current.tempC}",
                        iconUrl = "https:${weatherResponse.current.condition.icon}",
                        humidity = "${weatherResponse.current.humidity}",
                        uvIndex = "${weatherResponse.current.uv}",
                        feelsLike = "${weatherResponse.current.feelslikeC}"
                    ),
                    query = savedCity
                )
            } else {
                _weatherState.value = WeatherState.Empty
            }
        }
    }

    fun loadWeather(city: String) {
        viewModelScope.launch {
            try {
                val response = getWeatherUseCase(city)
                repository.saveSelectedCity(city)
                _weatherState.value = WeatherState.Loaded(
                    weather = WeatherData(
                        cityName = response.location.name,
                        temperature = "${response.current.tempC}",
                        iconUrl = "https:${response.current.condition.icon}",
                        humidity = "${response.current.humidity}",
                        uvIndex = "${response.current.uv}",
                        feelsLike = "${response.current.feelslikeC}"
                    ),
                    query = city
                )
            } catch (e: Exception) {
                _weatherState.value = WeatherState.NoResults(city)
            }
        }
    }
}