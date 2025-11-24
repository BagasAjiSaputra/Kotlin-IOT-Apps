package com.hydrosense.corp.ui.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hydrosense.corp.domain.model.SensorHistory
import com.hydrosense.corp.data.repository.SensorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: SensorRepository) : ViewModel() {

    private val _historyList = MutableStateFlow<List<SensorHistory>>(emptyList())
    val historyList: StateFlow<List<SensorHistory>> = _historyList

    private val _status = MutableStateFlow("")
    val status: StateFlow<String> = _status

    init {
        fetchHistory()
    }

    private fun fetchHistory() {
        viewModelScope.launch {
            try {
                val data = repository.getHistory()
                _historyList.value = data
                _status.value = "Data loaded successfully"
            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = "Failed to load data"
            }
        }
    }
}
