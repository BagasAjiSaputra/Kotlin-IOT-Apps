package com.hydrosense.corp.ui.screen.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hydrosense.corp.R
import com.hydrosense.corp.ui.components.DotChart
import com.hydrosense.corp.ui.theme.BgMain
import com.hydrosense.corp.ui.theme.Typography

@Composable
fun ChartScreen(vm: ChartViewModel = viewModel()) {
    // Dipanggil saat Composable pertama kali masuk ke komposisi
    LaunchedEffect(Unit) { vm.fetchSensorData() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgMain)
            .padding(16.dp)

    ) {

        Text(
            text = "Analytics",
            color = Color.White,
            style = Typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Sensor Chart",
            color = Color.Gray,
            style = Typography.labelLarge
        )

        Spacer(Modifier.height(16.dp))

        // --- Periksa Error dan Langsung Tampilkan/Return ---
        vm.errorMessage?.let { message ->
            Box(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error, // Warna merah untuk error
                    style = Typography.bodyMedium.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
                    modifier = Modifier.padding(16.dp)
                )
            }
            // Hentikan rendering sisa konten jika ada error
            return
        }
        // ------------------------------------------------

        // Konten Chart (Hanya dirender jika tidak ada error)
        val validData = vm.sensorData.filter { it.humidity != null && it.temperature != null }
            .reversed()

        if (validData.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))

                val progress by animateLottieCompositionAsState(
                    composition,
                    iterations = 20, // play 1x
                    restartOnPlay = false
                )

                LottieAnimation(
                    composition,
                    progress,
                    modifier = Modifier.size(100.dp) // Ukuran animasi
                )
            }
        } else {
            // Data Chart
            Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {

                // Map ke Pair<Int, Int> untuk Y
                val humidityData = validData.mapIndexed { index, sensor ->
                    index to (sensor.humidity ?: 0)
                }
                val temperatureData = validData.mapIndexed { index, sensor ->
                    index to ((sensor.temperature ?: 0.0).toInt())
                }

                // Ambil jam saja dari waktu

                val timeLabels = validData.map { sensor ->
                    sensor.time?.let { timeStr ->
                        try {
                            val parts = timeStr.split(" ")
                            if (parts.size >= 3) {
                                parts[0] // jam AM/PM
                            } else ""
                        } catch (e: Exception) {
                            ""
                        }
                    } ?: ""
                }

                Text(
                    "Humidity Chart",
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(2.dp))
                DotChart(
                    data = humidityData,
                    labels = timeLabels,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Temperature Chart",
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(2.dp))
                DotChart(
                    data = temperatureData,
                    labels = timeLabels,
                )
            }
        }
    }
}