package com.hydrosense.corp.ui.screen.chart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.hydrosense.corp.data.remote.ApiService
import com.hydrosense.corp.data.remote.Prefs
import com.hydrosense.corp.domain.model.SensorData
import kotlinx.coroutines.launch

class ChartViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = Prefs(application)
    private val apiService = ApiService(prefs.getIp())

    // pastikan tipe SensorData
    var sensorData by mutableStateOf(listOf<SensorData>())

    fun fetchSensorData() {
        viewModelScope.launch {
            val response = apiService.getSensorData() // List<SensorData>
            sensorData = response.map { it } // pastikan type List<SensorData>
        }
    }
}
