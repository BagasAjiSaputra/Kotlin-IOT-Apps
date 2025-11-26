package com.hydrosense.corp.ui.screen.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.hydrosense.corp.data.remote.Prefs
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.hydrosense.corp.data.remote.ApiService

class SettingViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = Prefs(application)
    private lateinit var apiService: ApiService

    // State user input
    var pin by mutableStateOf("")
    var recovery by mutableStateOf("")

    // Base URL state
    var baseUrl by mutableStateOf("")

    // response dummy
    var responseText by mutableStateOf("")

    init {
        // Ambil default URL dari Prefs
        baseUrl = prefs.getIp()
        apiService = ApiService(baseUrl) // Inisialisasi ApiService
    }

    fun saveBaseUrl() {
        prefs.saveIp(baseUrl)
        apiService = ApiService(baseUrl) // Perbarui ApiService dengan URL baru
        responseText = "Base URL disimpan: $baseUrl"
    }

    fun sendToApi() {
        if (baseUrl.isBlank()) {
            responseText = "Base URL harus diisi terlebih dahulu."
            return
        }

        // rekuest API secara async
        viewModelScope.launch {
            responseText = "Mengirim data..."

            // 1. Kirim PIN
            val pinResponse = apiService.updatePin(pin)
            responseText = "Hasil PIN: $pinResponse"

            // 2. Kirim Recovery Code
            val recoveryResponse = apiService.updateRecovery(recovery)
            responseText += "\nHasil Recovery: $recoveryResponse"
        }
    }
}
