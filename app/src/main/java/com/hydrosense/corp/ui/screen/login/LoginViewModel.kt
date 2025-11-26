package com.hydrosense.corp.ui.screen.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.hydrosense.corp.data.remote.ApiService
import com.hydrosense.corp.data.remote.Prefs // Harus ada
import com.hydrosense.corp.domain.model.AuthResponse

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = Prefs(application)
    private lateinit var apiService: ApiService
    private val baseUrl: String

    // State Input
    var inputPin by mutableStateOf("")
    var inputRecoveryCode by mutableStateOf("")

    // State UI
    var responseMessage by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var loginSuccess by mutableStateOf(false) // Untuk pemicu navigasi

    init {
        // Ambil Base URL dari Prefs (diasumsikan sudah di-save dari SettingScreen)
        baseUrl = prefs.getIp()
        apiService = ApiService(baseUrl)
    }

    // Fungsi Login PIN
    fun attemptLogin(onSuccess: () -> Unit) {
        if (inputPin.isBlank() || baseUrl.isBlank()) {
            responseMessage = "PIN atau Base URL tidak boleh kosong."
            return
        }

        isLoading = true
        responseMessage = "Mencoba login..."

        viewModelScope.launch {
            val result: AuthResponse = apiService.loginPin(inputPin)

            isLoading = false
            responseMessage = result.message
            loginSuccess = result.success

            if (result.success) {
                // Simpan state login (misalnya di Prefs)
                onSuccess() // Panggil callback navigasi
            }
        }
    }

    // Fungsi Login Recovery
    fun attemptRecoveryLogin(onSuccess: () -> Unit) {
        if (inputRecoveryCode.isBlank() || baseUrl.isBlank()) {
            responseMessage = "Recovery Code atau Base URL tidak boleh kosong."
            return
        }

        isLoading = true
        responseMessage = "Mencoba Recovery Login..."

        viewModelScope.launch {
            val result: AuthResponse = apiService.loginRecovery(inputRecoveryCode)

            isLoading = false
            responseMessage = result.message
            loginSuccess = result.success

            if (result.success) {
                // Biasanya setelah recovery, user diarahkan ke layar update PIN
                onSuccess() // Panggil callback navigasi (ke Home atau Update PIN)
            }
        }
    }
}