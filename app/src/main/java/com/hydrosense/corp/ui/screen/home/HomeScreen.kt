package com.hydrosense.corp.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hydrosense.corp.R
import com.hydrosense.corp.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(vm: HomeScreenViewModel = viewModel()) {
    LaunchedEffect(key1 = Unit) {
        vm.loadLatestData()
    }

    val latestData = vm.latestSensorData
    val statusMessage = vm.statusMessage
    val isLoading = vm.isLoading

    val isDataEmpty = latestData.id == 0 && latestData.soil == null


    Scaffold(
        containerColor = BgMain
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 5.dp, vertical = 5.dp)
        ) {
            // Reload Button dan Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tampilkan status message
                Text(
                    text = statusMessage,
                    color = if (statusMessage.contains("Gagal", ignoreCase = true)) AccentRed else AccentGreen,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = { vm.refreshAllData() },
                    enabled = !isLoading,
                    modifier = Modifier
                        .size(48.dp)
                        .background(BgRed, CircleShape)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Muat Ulang Data",
                            tint = AccentRed
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            var isTimeout by remember { mutableStateOf(false) }

            // Timer 3 detik loading
            LaunchedEffect(isLoading) {
                if (isLoading) {
                    delay(3000)
                    isTimeout = true
                } else {
                    isTimeout = false
                }
            }

            when {
                isLoading && isDataEmpty && !isTimeout -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = AccentGreen)
                    }
                }

                isDataEmpty -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))
                        val progress by animateLottieCompositionAsState(
                            composition,
                            iterations = 20,
                            restartOnPlay = false
                        )
                        LottieAnimation(
                            composition,
                            progress,
                            modifier = Modifier.size(150.dp)
                        )
                        Text(
                            text = "Something Wrong, Check internet !",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 200.dp)
                        )
                    }
                }

                else -> {
                    // --- Device Status Card ---
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(bottom = 16.dp),
//                        shape = RoundedCornerShape(12.dp),
//                        colors = CardDefaults.cardColors(containerColor = cardColorDarkGreen)
//                    ) {
//                        Column(modifier = Modifier.padding(16.dp)) {
//                            Row(verticalAlignment = Alignment.CenterVertically) {
//
//                                Box(
//                                    modifier = Modifier
//                                        .size(48.dp)
//                                        .background(
//                                            color = BgGreen,
//                                            shape = RoundedCornerShape(12.dp)
//                                        ),
//                                    contentAlignment = Alignment.Center // Memastikan ikon berada di tengah Box,
//                                ) {
//                                    // Konten: Ikon
//                                    Icon(
//                                        painter = painterResource(id = R.drawable.power),
//                                        contentDescription = "power",
//                                        modifier = Modifier.size(24.dp), // Ukuran ikon di dalam Box
//                                        tint = AccentGreen // 🌟 Menerapkan warna aksen ke ikon
//                                    )
//                                }
//
//                                Spacer(Modifier.width(8.dp))
//
//                                Column(
//
//                                ) {
//                                    Text(
//                                        text = "Device Status",
//                                        color = Color.White,
//                                        fontSize = 16.sp,
//                                        fontWeight = FontWeight.Medium
//                                    )
//                                    Spacer(Modifier.width(4.dp))
//                                    Text(
//                                        text = "● Online",
//                                        color = AccentGreen,
//                                        fontSize = 14.sp
//                                    )
//                                }
//                            }
//                            Spacer(Modifier.height(12.dp))
//                            Row(modifier = Modifier.fillMaxWidth()) {
//                                Column(modifier = Modifier.weight(1f)) {
//                                    Text(text = "Last Read Time", color = Color.Gray, fontSize = 12.sp)
//                                    Text(text = latestData.time, color = Color.White, fontSize = 14.sp)
//                                }
//                                // Karena kita tidak punya "Last Time OFF" di SensorData, kita bisa hapus ini
//                                // Atau biarkan kosong/tampilkan N/A
//                                /*
//                                Column(modifier = Modifier.weight(1f)) {
//                                    Text(text = "Last Time OFF", color = Color.Gray, fontSize = 12.sp)
//                                    Text(text = "Oct 21, 12:20", color = Color.White, fontSize = 14.sp)
//                                    Text(text = "2h 15m ago", color = Color.Gray, fontSize = 12.sp)
//                                }
//                                */
//                            }
//                            Spacer(Modifier.height(12.dp))
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Text(text = "Current State", color = Color.Gray, fontSize = 12.sp)
//                                Text(text = "Active", color = AccentGreen, fontSize = 14.sp) // Hardcoded atau disesuaikan jika ada state lain
//                            }
//                        }
//                    }

                    // Card Moisture & Temperature
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Soil Card
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = cardColorDarkRed)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {

                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(
                                            color = BgRed,
                                            shape = RoundedCornerShape(12.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.water),
                                        contentDescription = "Moisture",
                                        modifier = Modifier.size(24.dp),
                                        tint = AccentRed
                                    )
                                }

                                Spacer(Modifier.height(8.dp))
                                Text(text = "Soil Moisture", color = Color.White, fontSize = 14.sp)
                                Text(text = "● Active", color = AccentRed, fontSize = 12.sp)
                                Spacer(Modifier.height(8.dp))
                                Text(text = "${latestData.soil ?: "N/A"}%", color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Medium)
                                Text(
                                    text = getSoilMoistureStatus(latestData.soil),
                                    color = getSoilMoistureStatusColor(latestData.soil),
                                    fontSize = 14.sp
                                )
                            }
                        }

                        // Humidity Card
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = cardColorDarkBlue)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {

                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(
                                            color = BgBlue,
                                            shape = RoundedCornerShape(12.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.humidity),
                                        contentDescription = "Humidity",
                                        modifier = Modifier.size(24.dp),
                                        tint = AccentBlue
                                    )
                                }

                                Spacer(Modifier.height(8.dp))
                                Text(text = "Humidity", color = Color.White, fontSize = 14.sp)
                                Text(text = "● Active", color = AccentBlue, fontSize = 12.sp)
                                Spacer(Modifier.height(8.dp))
                                Text(text = "${latestData.humidity ?: "N/A"}%", color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Medium)
                                Text(
                                    text = getHumidityStatus(latestData.humidity),
                                    color = getHumidityStatusColor(latestData.humidity),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Temperature Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = cardColorDarkBlue)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(
                                        color = BgBlue,
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                // Konten: Ikon
                                Icon(
                                    painter = painterResource(id = R.drawable.thermometer),
                                    contentDescription = "Temperature",
                                    modifier = Modifier.size(24.dp),
                                    tint = AccentBlue
                                )
                            }

                            Spacer(Modifier.height(8.dp))

                            Text(text = "Temperature", color = AccentBlue, fontSize = 14.sp)
                            Text(
                                text = getTemperatureStatus(latestData.temperature),
                                color = getTemperatureStatusColor(latestData.temperature),
                                fontSize = 14.sp
                            )
//                            Spacer(Modifier.height(8.dp))
                            Text(text = "${latestData.temperature?.let { "%.1f".format(it) } ?: "N/A"}°C", color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
    }
}

// Helper untuk warna status
private fun getSoilMoistureStatus(soil: Int?): String {
    return when (soil) {
        null -> "N/A"
        in 0..20 -> "Very Low"
        in 21..40 -> "Low"
        in 41..60 -> "Optimal"
        in 61..80 -> "High"
        else -> "Very High"
    }
}

private fun getSoilMoistureStatusColor(soil: Int?): Color {
    return when (soil) {
        null -> Color.Gray
        in 0..20 -> AccentRed
        in 21..40 -> AccentRed
        in 41..60 -> Color(0xFFFFA500)
        in 61..80 -> AccentGreen
        else -> AccentGreen
    }
}

private fun getHumidityStatus(humidity: Int?): String {
    return when (humidity) {
        null -> "N/A"
        in 0..30 -> "Low"
        in 31..60 -> "Normal"
        else -> "High"
    }
}

private fun getHumidityStatusColor(humidity: Int?): Color {
    return when (humidity) {
        null -> Color.Gray
        in 0..30 -> AccentRed
        in 31..60 -> Color(0xFF87CEEB)
        else -> AccentGreen
    }
}

private fun getTemperatureStatus(temp: Double?): String {
    return when (temp) {
        null -> "N/A"
        in 0.0..15.0 -> "● Cold"
        in 15.1..28.0 -> "● Normal"
        else -> "● Hot"
    }
}

private fun getTemperatureStatusColor(temp: Double?): Color {
    return when (temp) {
        null -> Color.Gray
        in 0.0..15.0 -> AccentGreen
        in 15.1..28.0 -> AccentBlue
        else -> AccentRed // Merah
    }
}

// Fungsi helper untuk parsing waktu
private fun parseTimeToMillis(timeString: String): Long {
    return System.currentTimeMillis()
}

