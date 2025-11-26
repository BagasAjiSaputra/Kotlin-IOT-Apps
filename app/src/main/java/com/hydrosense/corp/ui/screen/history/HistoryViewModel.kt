package com.hydrosense.corp.ui.screen.history

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
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class DataViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = Prefs(application)
    private lateinit var apiService: ApiService

    var baseUrl by mutableStateOf("")
    var sensorList by mutableStateOf<List<SensorData>>(emptyList())

    // State untuk mengontrol status loading
    var isLoading by mutableStateOf(false)
    var statusMessage by mutableStateOf("")

    init {
        baseUrl = prefs.getIp()
        apiService = ApiService(baseUrl)
    }

//    fun saveBaseUrl() {
//        prefs.saveIp(baseUrl)
//        apiService = ApiService(baseUrl)
//        statusMessage = "Base URL saved"
//    }

    fun loadData() {
        viewModelScope.launch {
            isLoading = true
            statusMessage = "Fetching Data"

            try {
                // Ambil data
                val data = apiService.getSensorData()
                sensorList = data // Timpa data (penimpa handals)


                statusMessage = if (sensorList.isEmpty()) {
                    "Something Wrong !"
                } else {
                    "Fetching Data (${sensorList.size} item)."
                }
            } catch (e: Exception) {
                // Handling error lain
                val errorMessage = when (e) {
                    is UnknownHostException, is ConnectException ->
                        "Connection Error !"
                    is SocketTimeoutException ->
                        "Request Timeout !"
                    else ->
                        "Failed Fetching Data (Error: ${e.message})"
                }
                statusMessage = errorMessage
            } finally {
                // 5. Akhiri Loading
                isLoading = false
            }
        }
    }
}