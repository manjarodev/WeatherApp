package com.test.weather.ui.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.test.weather.domain.viewmodel.WeatherState
import com.test.weather.domain.viewmodel.WeatherViewModel
import com.test.weather.ui.home.HomeEmptyState
import com.test.weather.ui.home.HomeScreen
import com.test.weather.ui.result.NoResultsScreen
import com.test.weather.ui.search.SearchResultsScreen

@Composable
fun MainScreen(viewModel: WeatherViewModel) {
    val state by viewModel.weatherState.collectAsState()
    when (state) {
        is WeatherState.Empty -> HomeEmptyState(
            query = "",
            onQueryChanged = { viewModel.updateQuery(it) },
            onSearchClicked = { viewModel.searchCity() }
        )

        is WeatherState.Loaded -> {
            val weather = (state as WeatherState.Loaded).weather
            HomeScreen(
                cityName = weather.cityName,
                temperature = weather.temperature,
                weatherIconUrl = weather.iconUrl,
                humidity = weather.humidity,
                uvIndex = weather.uvIndex,
                feelsLike = weather.feelsLike,
                query = "",
                onQueryChanged = { viewModel.updateQuery(it) },
                onSearchClicked = { viewModel.searchCity() }
            )
        }

        is WeatherState.SearchResults -> {
            val searchResults = (state as WeatherState.SearchResults).results
            SearchResultsScreen(
                searchResults = searchResults,
                onResultClicked = { city ->
                    viewModel.loadWeather(city)
                }
            )
        }

        is WeatherState.NoResults -> NoResultsScreen(
            query = (state as WeatherState.NoResults).query,
            onQueryChanged = { viewModel.updateQuery(it) },
            onSearchClicked = { viewModel.searchCity() }
        )
    }
}