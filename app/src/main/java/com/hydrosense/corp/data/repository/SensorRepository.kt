package com.hydrosense.corp.data.repository

import com.hydrosense.corp.domain.model.TestModel
import com.hydrosense.corp.data.remote.SensorApi

class SensorRepository(private val api: SensorApi) {
    suspend fun getHistory(): List<TestModel> {
        return api.getSensorHistory().map { dto ->
            TestModel(
                time = dto.time,
                soil = dto.soil,
                humidity = dto.humidity,
                temperature = dto.temperature
            )
        }
    }
}
