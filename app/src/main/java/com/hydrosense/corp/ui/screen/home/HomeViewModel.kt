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
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class HomeScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = Prefs(application)
    private lateinit var apiService: ApiService

    // Default data saat belum ada data dimuat atau gagal.
    // Dibuat sesuai dengan konstruktor SensorData baru (yang membutuhkan 'id').
    private val defaultSensorData = SensorData(id = 0, time = "N/A", soil = null, humidity = null, temperature = null)

    var baseUrl by mutableStateOf("")

    // ⭐ Menyimpan data sensor TUNGGAL dan TERAKHIR
    var latestSensorData by mutableStateOf<SensorData>(defaultSensorData)

    var isLoading by mutableStateOf(false)
    var statusMessage by mutableStateOf("")

    init {
        baseUrl = prefs.getIp()
        // Asumsi: apiService diinisialisasi ulang jika baseUrl berubah
        apiService = ApiService(baseUrl)
    }

    // ⭐ Fungsi untuk memuat data sensor TERAKHIR
//    fun loadLatestData() {
//        viewModelScope.launch {
//            isLoading = true
//            statusMessage = "Mengambil data sensor terakhir..."
//
//            try {
//                // 1. Ambil SEMUA data (List<SensorData>)
//                val dataList = apiService.getSensorData()
//
//                // 2. Ambil item PERTAMA (yang diasumsikan paling baru/terakhir dari API)
//                latestSensorData = dataList.firstOrNull() ?: defaultSensorData
//
//                // 3. Pemuatan Berhasil
//                statusMessage = if (latestSensorData.soil == null && latestSensorData.id == 0) {
//                    "Data sensor terakhir kosong."
//                } else {
//                    "Data terakhir berhasil dimuat dari ${latestSensorData.time}."
//                }
//            } catch (e: Exception) {
//                // 4. Tangani Kegagalan
//                val errorMessage = when (e) {
//                    is UnknownHostException, is ConnectException ->
//                        "Gagal Koneksi: Periksa alamat IP/koneksi jaringan."
//                    is SocketTimeoutException ->
//                        "Gagal Timeout: Waktu pengambilan data habis."
//                    else ->
//                        "Gagal mengambil data. (Error: ${e.message})"
//                }
//                statusMessage = errorMessage
//                latestSensorData = defaultSensorData // Reset ke default saat gagal
//            } finally {
//                // 5. Akhiri Loading
//                isLoading = false
//            }
//        }
//    }

    // Trigger
    var triggerResponseText by mutableStateOf("")
        private set

    init {
        // Ambil Base URL dan inisialisasi ApiService di sini
        val baseUrl = prefs.getIp()
        apiService = ApiService(baseUrl)
    }

    fun sendTriggerRequest() {
        triggerResponseText = "Mengirim permintaan trigger..."

        viewModelScope.launch {
            val result = apiService.triggerRefresh(shouldRefresh = true)
            triggerResponseText = result
        }
    }

    fun loadLatestData(delayBeforeLoad: Boolean = false) { // Tambahkan parameter dengan nilai default 'false'
        viewModelScope.launch {

            // Terapkan delay jika dipanggil dari refreshAllData()
            if (delayBeforeLoad) {
                delay(1000) // Delay 1 detik (1000ms)
            }

            isLoading = true
            statusMessage = "Mengambil data sensor terakhir..."

            try {
                // 1. Ambil SEMUA data (List<SensorData>)
                val dataList = apiService.getSensorData()

                // 2. Ambil item PERTAMA
                latestSensorData = dataList.firstOrNull() ?: defaultSensorData

                // 3. Pemuatan Berhasil
                statusMessage = if (latestSensorData.soil == null && latestSensorData.id == 0) {
                    "Data sensor terakhir kosong."
                } else {
                    "Data terakhir berhasil dimuat dari ${latestSensorData.time}."
                }
            } catch (e: Exception) {
                // ... (Logika penanganan error)
            } finally {
                isLoading = false
            }
        }
    }

    // ⭐ Fungsi Gabungan: Memuat data sensor DAN mengirim trigger
    fun refreshAllData() {
        viewModelScope.launch {
            isLoading = true
            statusMessage = "Mengirim trigger API dan memuat data..."

            // 1. Kirim Trigger Refresh ke API
            val triggerResult = try {
                sendTriggerRequest()
            } catch (e: Exception) {
                "Gagal mengirim Trigger: ${e.message}"
            }

            // 2. Muat Data Sensor
            loadLatestData(delayBeforeLoad = true) // Panggil fungsi muat data yang sudah ada

            // Perbarui status akhir setelah kedua operasi selesai
            statusMessage = "Trigger Status: ${triggerResult}. Data Status: ${statusMessage}"
            isLoading = false
        }
    }
}