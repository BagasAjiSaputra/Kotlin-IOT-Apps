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
    // Asumsi ApiService dapat dilewatkan/dibuat di ViewModel
    // Pastikan ini terinisialisasi dengan benar
    private val apiService = ApiService(prefs.getIp())

    // pastikan tipe SensorData
    var sensorData by mutableStateOf(listOf<SensorData>())

    // State untuk menyimpan pesan error
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchSensorData() {
        // Reset error message sebelum fetch baru
        errorMessage = null
        viewModelScope.launch {
            try {
                // List<SensorData>
                val response = apiService.getSensorData()
                sensorData = response.map { it } // pastikan type List<SensorData>
            } catch (e: IOException) {
                // Tangani error koneksi/jaringan (misalnya, gagal fetch)
                errorMessage = "Gagal mengambil data. Pastikan koneksi internet Anda aktif."
                // Opsional: Log error untuk debugging
                e.printStackTrace()
            } catch (e: Exception) {
                // Tangani error lain (misalnya, parsing error)
                errorMessage = "Terjadi kesalahan: ${e.message ?: "Unknown Error"}"
                e.printStackTrace()
            }
        }
    }
}