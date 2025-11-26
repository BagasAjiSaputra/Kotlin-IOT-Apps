package com.hydrosense.corp.ui.screen.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.hydrosense.corp.data.remote.ApiService
import com.hydrosense.corp.data.remote.Prefs
import com.hydrosense.corp.domain.model.SensorData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = Prefs(application)
    private lateinit var apiService: ApiService
    private val defaultSensorData = SensorData(id = 0, time = "N/A", soil = null, humidity = null, temperature = null)

    var baseUrl by mutableStateOf("")

    // Menyimpan data sensor Terakhir
    var latestSensorData by mutableStateOf<SensorData>(defaultSensorData)

    var isLoading by mutableStateOf(false)
    var statusMessage by mutableStateOf("")

    init {
        baseUrl = prefs.getIp()
        apiService = ApiService(baseUrl)
    }


    // Trigger
    var triggerResponseText by mutableStateOf("")
        private set

    init {
        // Ambil Base URL dan inisialisasi ApiService
        val baseUrl = prefs.getIp()
        apiService = ApiService(baseUrl)
    }

    fun sendTriggerRequest() {
        triggerResponseText = "Send Request trigger..."

        viewModelScope.launch {
            val result = apiService.triggerRefresh(shouldRefresh = true)
            triggerResponseText = result
        }
    }

    fun loadLatestData(delayBeforeLoad: Boolean = false) {
        viewModelScope.launch {

            if (delayBeforeLoad) {
                delay(1000)
            }

            isLoading = true
            statusMessage = "Fetch Last Data Snensor"

            try {
                // Ambil SEMUA data List SensorData
                val dataList = apiService.getSensorData()

                // Ambil item PERTAMA
                latestSensorData = dataList.firstOrNull() ?: defaultSensorData

                statusMessage = if (latestSensorData.soil == null && latestSensorData.id == 0) {
                    "Last Data Empty"
                } else {
                    "Last Data Loaded : ${latestSensorData.time}."
                }
            } catch (e: Exception) {
                // Jika gagal kosonk
            } finally {
                isLoading = false
            }
        }
    }

    // 2 Fungsi jadi 1 trigger dan get
    fun refreshAllData() {
        viewModelScope.launch {
            isLoading = true
            statusMessage = "Mengirim trigger API dan memuat data..."

            // Kirim Trigger Refresh ke API
            val triggerResult = try {
                sendTriggerRequest()
            } catch (e: Exception) {
                "Gagal mengirim Trigger: ${e.message}"
            }

            // Muat Data Sensor
            loadLatestData(delayBeforeLoad = true) // Panggil fungsi muat data yang sudah ada

            // Perbarui data
            statusMessage = "Trigger Status: ${triggerResult}. Data Status: ${statusMessage}"
            isLoading = false
        }
    }
}