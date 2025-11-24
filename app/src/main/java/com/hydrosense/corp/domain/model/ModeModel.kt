package com.hydrosense.corp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SmartConfig(
    val idle: Int = 0,
    val sleep: Int = 0,
    val duration: Int = 0
)

@Serializable
data class ManualConfig(
    val state: String = "close", // "open" / "close"
    val duration: Int = 0
)

@Serializable
data class ModeResponse(
    val mode: String,
    val smart: SmartConfig,
    val manual: ManualConfig,
    val success: Boolean? = null, // hanya ada di POST response
    val error: String? = null     // jika error
)

@Serializable
data class ModeRequest(
    val mode: String,
    val idle: Int? = null,
    val sleep: Int? = null,
    val duration: Int? = null,
    val servo: String? = null
)
