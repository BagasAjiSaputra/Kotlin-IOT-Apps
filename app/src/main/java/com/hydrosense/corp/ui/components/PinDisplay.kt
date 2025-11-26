package com.hydrosense.corp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons // <-- PENTING: Untuk ikon backspace
import androidx.compose.material.icons.filled.ArrowBack // <-- PENTING: Untuk ikon backspace
import androidx.compose.material3.* // Untuk Card, Icon, dll.
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hydrosense.corp.ui.theme.*

private const val MAX_PIN_LENGTH = 6

@Composable
fun PinDisplay(pin: String, length: Int = MAX_PIN_LENGTH) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(length) { index ->
            val isFilled = index < pin.length

            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(
                        color = if (isFilled) AccentGreen else AccentGreen.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(8.dp)
                    )
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}