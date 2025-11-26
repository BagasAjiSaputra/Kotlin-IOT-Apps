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
import java.io.IOException

class ChartViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = Prefs(application)
    private val apiService = ApiService(prefs.getIp())
    var sensorData by mutableStateOf(listOf<SensorData>())
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchSensorData() {
        errorMessage = null
        viewModelScope.launch {
            try {
                // List Sensor Data
                val response = apiService.getSensorData()
                sensorData = response.map { it }
            } catch (e: IOException) {
                // Tangani error konegsi
                errorMessage = "Failed to Fetch, Check Internet"
                // Log Debuggink
                e.printStackTrace()
            } catch (e: Exception) {
                errorMessage = "Something Wrong : ${e.message ?: "Unknown Error"}"
                e.printStackTrace()
            }
        }
    }
}