package com.hydrosense.corp.ui.components

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

@Composable
fun Numpad(
    onNumberClick: (String) -> Unit,
    onDeleteClick: () -> Unit
) {
    // Angka 1-9 dan 0
    val keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", "DEL")

    // Grid 4 baris, 3 kolom
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        keys.chunked(3).forEach { rowKeys ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowKeys.forEach { key ->
                    NumpadButton(
                        key = key,
                        onClick = {
                            when (key) {
                                "DEL" -> onDeleteClick()
                                // Abaikan tombol kosong
                                "" -> {/* Do nothing */ }
                                else -> onNumberClick(key)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.NumpadButton(
    key: String,
    onClick: () -> Unit
) {
    val isDelete = key == "DEL"
    val isEmpty = key == ""

    Box(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
            .padding(8.dp)
            .clickable(onClick = onClick, enabled = !isEmpty),
        contentAlignment = Alignment.Center
    ) {
        if (!isEmpty) {
            Card(
//                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDelete) AccentGreen.copy(alpha = 0.2f) else AccentGreen.copy(alpha = 0.4f) // Warna tombol
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (isDelete) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Delete",
                            tint = AccentGreen,
                            modifier = Modifier.size(32.dp)
                        )
                    } else {
                        Text(
                            text = key,
                            color = AccentGreen,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}