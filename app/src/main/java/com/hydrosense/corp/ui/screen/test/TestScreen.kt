package com.hydrosense.corp.ui.screen.test

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hydrosense.corp.R
import com.hydrosense.corp.ui.components.SensorCard
import com.hydrosense.corp.ui.theme.AccentGreen
import com.hydrosense.corp.ui.theme.*

@Composable
fun SensorDataScreen(vm: DataViewModel = viewModel()) {

    LaunchedEffect(key1 = Unit) {
        vm.loadData()
    }

    // Pastikan data sensorList diambil dengan benar dari ViewModel
    val sensorItems = vm.sensorList
    // Catatan: Asumsi vm.sensorList adalah StateFlow/LiveData/State

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp, vertical = 8.dp) // Sesuaikan padding
    ) {

//        OutlinedTextField(
//            value = vm.baseUrl,
//            onValueChange = { vm.baseUrl = it },
//            label = { Text("Base URL / IP") },
//            modifier = Modifier.fillMaxWidth()
//        )

        Spacer(Modifier.height(2.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), // Penting agar Row mengisi lebar penuh
            // Mengatur konten agar rata kanan
            horizontalArrangement = Arrangement.End
        ) {

            // Hapus: horizontalArrangement = Arrangement.spacedBy(12.dp)
            // Button sekarang ada di kanan

            Button(
                onClick = { vm.loadData() },
                // 1. Mengatur bentuk menjadi lingkaran
                shape = CircleShape,
                // 2. Mengatur warna tombol
                colors = ButtonDefaults.buttonColors(
                    containerColor = BgRed, // Warna latar belakang tombol (BgRed)
                    contentColor = AccentRed // Warna konten (ikon) di dalam tombol (AccentRed)
                ),
                // 3. Mengatur ukuran tombol menjadi kecil/lingkaran (misalnya 48dp)
                modifier = Modifier.size(48.dp),
                // 4. Menghilangkan padding internal agar ikon pas di tengah
                contentPadding = PaddingValues(0.dp)
            ) {
                // Ikon (painterResource(id = R.drawable.refresh) - asumsi ini ikon yang benar)
                Icon(
                    painter = painterResource(id = R.drawable.refresh),
                    contentDescription = "Muat Ulang Data",
                    modifier = Modifier.size(24.dp) // Ukuran ikon di dalam tombol
                )
            }

            // Jika Anda memiliki tombol lain, tambahkan Spacer di sini jika Arrangement.End tidak cukup
            // Contoh: Spacer(Modifier.width(12.dp))
        }

        Spacer(Modifier.height(16.dp))

//        Text(vm.statusMessage, color = AccentGreen)

        Spacer(Modifier.height(16.dp))

        // LazyColumn sekarang menggunakan komponen SensorCard kustom
        LazyColumn(
            // Hapus padding vertikal di sini, karena SensorCard sudah punya padding horizontal 16.dp
            verticalArrangement = Arrangement.spacedBy(16.dp), // Spasi antar card lebih besar
            modifier = Modifier.fillMaxSize()
        ) {
            items(sensorItems) { item -> // Ganti vm.sensorList dengan sensorItems (State)

                // 🌟 Ganti blok Card default dengan SensorCard kustom Anda
                SensorCard(
                    time = item.time,
                    // Pastikan properti soil, humidity, dan temperature di objek 'item'
                    // memiliki tipe data yang cocok (Int, Int, Double)
                    soil = item.soil ?: 0, // Beri nilai default jika null
                    humidity = item.humidity ?: 0, // Beri nilai default jika null
                    temperature = item.temperature ?: 0.0 // Beri nilai default jika null
                )
            }
        }
    }

}
