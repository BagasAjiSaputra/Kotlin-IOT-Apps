package com.hydrosense.corp.ui.screen.chart

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hydrosense.corp.ui.components.DotChart

@Composable
fun ChartScreen(vm: ChartViewModel = viewModel()) {
    LaunchedEffect(Unit) { vm.fetchSensorData() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Filter valid data
        val validData = vm.sensorData.filter { it.humidity != null && it.temperature != null }
            .reversed()

        // Map ke Pair<Int, Int> untuk Y
        val humidityData = validData.mapIndexed { index, sensor ->
            index to (sensor.humidity ?: 0)
        }
        val temperatureData = validData.mapIndexed { index, sensor ->
            index to ((sensor.temperature ?: 0.0).toInt())
        }

        // Ambil jam saja dari waktu
        val timeLabels = validData.map { sensor ->
            sensor.time?.let { timeStr ->
                try {
                    val parts = timeStr.split(" ")
                    if (parts.size >= 3) {
                        parts[0] // jam AM/PM
                    } else ""
                } catch(e: Exception) { "" }
            } ?: ""
        }

        Text(
            "Humidity Chart",
            color = Color.White
        )

        Spacer(modifier = Modifier.height(2.dp))
        DotChart(
            data = humidityData,
            labels = timeLabels,
//            dotColor = Color.Green
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Temperature Chart",
            color = Color.White
        )

        Spacer(modifier = Modifier.height(2.dp))
        DotChart(
            data = temperatureData,
            labels = timeLabels,
//            dotColor = Color.Blue
        )
    }
}
