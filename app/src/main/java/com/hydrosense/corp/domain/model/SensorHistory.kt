package com.hydrosense.corp.domain.model

data class SensorHistory(
    val time: String,
    val soil: Int,
    val humidity: Int,
    val temperature: Double
)

//data class SensorHistoryResponse(
//    val success: Boolean,
//    val history: List<SensorHistory>
//)
