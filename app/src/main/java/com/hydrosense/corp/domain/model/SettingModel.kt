package com.hydrosense.corp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PinRequest(
    val pin: String
)

@Serializable
data class RecoveryRequest(
    val recovery: String
)

@Serializable
data class ApiResponse(
    val message: String
)