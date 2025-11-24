package com.hydrosense.corp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PinRequest(
    val pin: String // Harus cocok dengan kunci 'pin' di API
)

@Serializable
data class RecoveryRequest(
    val recovery: String // Harus cocok dengan kunci 'recovery' di API
)

@Serializable
data class ApiResponse(
    val message: String
)