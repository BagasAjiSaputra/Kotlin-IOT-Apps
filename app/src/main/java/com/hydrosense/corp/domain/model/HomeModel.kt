package com.hydrosense.corp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TriggerRequest(
    val send: Boolean
)