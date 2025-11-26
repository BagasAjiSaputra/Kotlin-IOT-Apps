package com.hydrosense.corp.ui.screen.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hydrosense.corp.R
import com.hydrosense.corp.ui.components.SensorCard
import com.hydrosense.corp.ui.theme.* // Pastikan AccentRed ada di sini, atau ganti dengan Color.Red
import kotlinx.coroutines.delay

@Composable
fun HistoryScreen(vm: DataViewModel = viewModel()) {

    LaunchedEffect(key1 = Unit) {
        vm.loadData()
    }

    val sensorItems = vm.sensorList
    val statusMessage = vm.statusMessage // Ambil State dari ViewModel
    val isLoading = vm.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp, vertical = 8.dp)
    ) {

        // ... (Header Title dan Description)

        Text(
            text = "Sensor History",
            color = Color.White,
            style = Typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Past History Sensor Data",
            color = Color.Gray,
            style = Typography.labelLarge
        )

        Spacer(Modifier.height(2.dp))

        // --- Reload Button dan Status ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tampilkan status message
            Text(
                text = statusMessage,
                color = if (statusMessage.contains("Gagal")) AccentRed else AccentGreen,
                style = Typography.labelLarge
            )

            Button(
                onClick = { vm.loadData() },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BgRed,
                    contentColor = AccentRed
                ),
                modifier = Modifier.size(48.dp),
                contentPadding = PaddingValues(0.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.refresh),
                        contentDescription = "Muat Ulang Data",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        var isTimeout by remember { mutableStateOf(false) }

        // Timer 5 detik hanya saat loading terjadi
        LaunchedEffect(isLoading) {
            if (isLoading) {
                delay(3000)
                isTimeout = true
            } else {
                isTimeout = false
            }
        }

        when {

            // 🟡 1. Loading NORMAL (belum timeout)
            isLoading && sensorItems.isEmpty() && !isTimeout -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                    CircularProgressIndicator(
                        color = AccentGreen,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            // 🔴 2. TIMEOUT setelah 5 detik loading → Internet lambat
//            isLoading && sensorItems.isEmpty() && isTimeout -> {
//                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//
//                }
//            }

            // ⚪ 3. Fetch sukses tapi LIST kosong (bukan error)
            sensorItems.isEmpty() && !statusMessage.contains("Gagal", ignoreCase = true) -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {

                    val composition by rememberLottieComposition(
                        LottieCompositionSpec.RawRes(R.raw.error)
                    )

                    val progress by animateLottieCompositionAsState(
                        composition,
                        iterations = 20,
                        restartOnPlay = false
                    )

                    LottieAnimation(
                        composition = composition,
                        progress = progress,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }

            // 🟢 4. Data Ada
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(sensorItems) { item ->
                        SensorCard(
                            time = item.time,
                            soil = item.soil ?: 0,
                            humidity = item.humidity ?: 0,
                            temperature = item.temperature ?: 0.0
                        )
                    }
                }
            }
        }

    }
}