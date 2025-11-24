package com.hydrosense.corp.data.repository

import com.hydrosense.corp.domain.model.SensorHistory
import com.hydrosense.corp.data.remote.SensorApi

class SensorRepository(private val api: SensorApi) {
    suspend fun getHistory(): List<SensorHistory> {
        return api.getSensorHistory().map { dto ->
            SensorHistory(
                time = dto.time,
                soil = dto.soil,
                humidity = dto.humidity,
                temperature = dto.temperature
            )
        }
    }
}
