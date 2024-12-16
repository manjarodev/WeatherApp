package com.test.weather.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.test.weather.domain.viewmodel.WeatherViewModel
import com.test.weather.ui.theme.WeatherTrackerTheme
import com.test.weather.ui.ui.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTrackerTheme {
                val viewModel: WeatherViewModel = viewModel()
                MainScreen(viewModel = viewModel)
            }
        }
    }
}