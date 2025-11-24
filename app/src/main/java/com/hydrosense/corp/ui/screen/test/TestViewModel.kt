package com.hydrosense.corp.ui.screen.test

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

class DataViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = Prefs(application)
    private lateinit var apiService: ApiService

    var baseUrl by mutableStateOf("")
    var sensorList by mutableStateOf<List<SensorData>>(emptyList())
    var statusMessage by mutableStateOf("")

    init {
        baseUrl = prefs.getIp()
        apiService = ApiService(baseUrl)
    }

    fun saveBaseUrl() {
        prefs.saveIp(baseUrl)
        apiService = ApiService(baseUrl)
        statusMessage = "Base URL disimpan."
    }

    fun loadData() {
        viewModelScope.launch {
            statusMessage = "Mengambil data..."
            sensorList = apiService.getSensorData()

            statusMessage = if (sensorList.isEmpty()) {
                "Gagal mengambil data / data kosong."
            } else {
                "Data berhasil dimuat (${sensorList.size} item)."
            }
        }
    }
}
