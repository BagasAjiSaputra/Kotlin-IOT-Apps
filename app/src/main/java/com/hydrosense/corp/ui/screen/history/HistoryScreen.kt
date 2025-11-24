package com.hydrosense.corp.ui.screen.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hydrosense.corp.domain.model.SensorHistory
import com.hydrosense.corp.ui.components.*


@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    modifier: Modifier = Modifier
) {
    val historyList by viewModel.historyList.collectAsState(initial = emptyList())
    val status by viewModel.status.collectAsState(initial = "")

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(text = status, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(historyList) { sensor ->
                SensorCard(
                    time = sensor.time,
                    soil = sensor.soil,
                    humidity = sensor.humidity,
                    temperature = sensor.temperature
                )
            }
        }

    }
}
