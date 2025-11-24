package com.hydrosense.corp.ui.screen.mode

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hydrosense.corp.data.remote.ApiService
import com.hydrosense.corp.data.remote.Prefs
import com.hydrosense.corp.domain.model.ModeRequest
import com.hydrosense.corp.domain.model.ModeResponse
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class ModeViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = Prefs(application)
    private val api: ApiService = ApiService(prefs.getIp())

    // =============== UI STATE - TEXT FIELD ===============
    var smartIdleText by mutableStateOf("")
    var smartSleepText by mutableStateOf("")
    var smartDurationText by mutableStateOf("")
    var manualDurationText by mutableStateOf("")

    // =============== UI STATE - INTEGER ===============
    var currentMode by mutableStateOf("manual")

    var smartIdle by mutableStateOf(0)
    var smartSleep by mutableStateOf(0)
    var smartDuration by mutableStateOf(0)

    var manualState by mutableStateOf("close")
    var manualDuration by mutableStateOf(0)

    var responseText by mutableStateOf("")

    init {
        loadMode()
    }

    // ==================== GET ====================
    fun loadMode() {
        viewModelScope.launch {
            try {
                val res: ModeResponse = api.getMode()
                applyResponse(res)
            } catch (e: Exception) {
                responseText = "Gagal load mode: ${e.message}"
            }
        }
    }

    // ==================== POST ====================
    fun updateMode(request: ModeRequest) {
        viewModelScope.launch {
            try {
                val res = api.updateMode(request)
                applyResponse(res)
                responseText = "Berhasil update mode!"
            } catch (e: Exception) {
                responseText = "Gagal update: ${e.message}"
            }
        }
    }

    // ================= APPLY API RESPONSE =================
    private fun applyResponse(res: ModeResponse) {
        currentMode = res.mode

        // SMART
        smartIdle = res.smart.idle
        smartSleep = res.smart.sleep
        smartDuration = res.smart.duration

        smartIdleText = res.smart.idle.toString()
        smartSleepText = res.smart.sleep.toString()
        smartDurationText = res.smart.duration.toString()

        // MANUAL
        manualState = res.manual.state
        manualDuration = res.manual.duration
        manualDurationText = res.manual.duration.toString()
    }
}
