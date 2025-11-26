package com.hydrosense.corp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val pin: String
)

@Serializable
data class RecoveryLoginRequest(
    val code: String
)

@Serializable
data class AuthResponse(
    val success: Boolean,
    val message: String
)