package com.hydrosense.corp.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hydrosense.corp.domain.model.SensorHistory
import com.hydrosense.corp.ui.components.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hydrosense.corp.ui.screen.home.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val sensor by viewModel.latestSensor.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (sensor.time.isNotEmpty()) {
            BigSensorSection(
                time = sensor.time,
                soil = sensor.soil,
                humidity = sensor.humidity,
                temperature = sensor.temperature
            )
        } else {
            Text(
                text = "Loading...",
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}