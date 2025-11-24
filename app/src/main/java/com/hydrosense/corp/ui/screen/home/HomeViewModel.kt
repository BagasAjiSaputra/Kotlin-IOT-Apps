package com.hydrosense.corp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hydrosense.corp.domain.model.SensorHistory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    // StateFlow untuk data sensor terbaru
    private val _latestSensor = MutableStateFlow(
        SensorHistory(
            time = "",
            soil = 0,
            humidity = 0,
            temperature = 0.0
        )
    )
    val latestSensor: StateFlow<SensorHistory> = _latestSensor

    init {
        loadLatestSensor()
    }

    // Fungsi untuk load data, nanti bisa ganti fetch API
    fun loadLatestSensor() {
        viewModelScope.launch {

            // simulasi loading data delay (API)
            delay(500)

            // Dummy data
            _latestSensor.value = SensorHistory(
                time = "23 Nov 2025 01.05.01 P.M.",
                soil = 87,
                humidity = 69,
                temperature = 28.5
            )
        }
    }
}
