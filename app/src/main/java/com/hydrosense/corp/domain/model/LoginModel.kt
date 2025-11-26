package com.hydrosense.corp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val pin: String // Untuk login
)

@Serializable
data class RecoveryLoginRequest(
    val code: String // Untuk verifikasi recovery code
)

@Serializable
data class AuthResponse(
    val success: Boolean,
    val message: String
)