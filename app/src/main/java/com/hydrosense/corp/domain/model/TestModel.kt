package com.hydrosense.corp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SensorData(
    val id: Int,
    val time: String,
    val soil: Int? = null,
    val humidity: Int? = null,
    val temperature: Double? = null
)