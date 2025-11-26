package com.hydrosense.corp.ui.screen.mode

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hydrosense.corp.R
import com.hydrosense.corp.domain.model.ModeRequest
import com.hydrosense.corp.ui.theme.*


@Composable
fun ModeScreen(vm: ModeViewModel = viewModel()) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgMain)
            .padding(16.dp)
    ) {
        Text(
            text = "Control Center",
            color = Color.White,
            // 🌟 Tambahkan style untuk mempertegas sebagai Title
            style = Typography.bodyLarge
        )

        // Jarak antara Title dan Description
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Manage IoT Settings",
            color = Color.Gray, // Warna yang lebih lembut untuk deskripsi
            // 🌟 Tambahkan style untuk membedakan dari Title
            style = Typography.labelLarge
        )

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)) // ⭐ Sudut membulat pada seluruh kartu
                .background(BgSecond) // ⭐ Warna latar belakang kotak/kartu
                .padding(10.dp) // Padding internal agar konten tidak menempel ke tepi Box
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // 🌟 KOREKSI: Mengganti Column yang salah dengan Box
                    Box(

                        modifier = Modifier
                            .size(48.dp) // Ukuran Box (lingkaran luar)
                            // Menerapkan warna latar belakang dan bentuk lingkaran (BgRed)
                            .background(
                                color = BgGreen,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center // Memastikan ikon berada di tengah Box,
                    ) {
                        // Konten: Ikon
                        Icon(
                            painter = painterResource(id = R.drawable.electric),
                            contentDescription = "Mode Desc",
                            modifier = Modifier.size(24.dp), // Ukuran ikon di dalam Box
                            tint = AccentGreen // 🌟 Menerapkan warna aksen ke ikon
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp)) // Memberi jarak antara ikon dan teks

                    // Column untuk menampung Dua Teks (mengisi sisa ruang)
                    Column(
                        modifier = Modifier.weight(1f) // Tetap menggunakan weight(1f)
                    ) {
                        // Title
                        Text(
                            text = "Operation Mode",
                            color = Color.White,
                            style = Typography.labelLarge
                        )

                        // Description
                        Text(
                            text = "Choose Mode Between Auto & Manual",
                            color = Color.Gray,
                            style = Typography.labelSmall
                        )
                    }
                }

                // ===== TOGGLE SWITCH (BAGIAN YANG DIUBAH) =====
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Manual", color = AccentGreen)

                    Switch(
                        checked = vm.currentMode == "smart",
                        onCheckedChange = { isChecked ->
                            val newMode = if (isChecked) "smart" else "manual"

                            // ⭐ PERUBAHAN UNTUK OFFLINE-FRIENDLY UI:
                            // Langsung ubah state lokal (vm.currentMode)
                            // agar UI (switch dan card) segera berubah.
                            vm.currentMode = newMode

                            // Lakukan panggilan API di latar belakang
                            vm.updateMode(ModeRequest(mode = newMode))
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = AccentGreen,
                            checkedTrackColor = AccentGreen.copy(alpha = 0.4f),
                            uncheckedThumbColor = AccentBlue,
                            uncheckedTrackColor = AccentBlue.copy(alpha = 0.4f)
                        )
                    )

                    Text("Smart", color = AccentGreen)
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // ===== MODE CARD (Menggunakan vm.currentMode yang sudah diubah) =====
        if (vm.currentMode == "manual") {
            ModeCard(bg = BgSecond) { ManualSection(vm) }
        } else {
            ModeCard(bg = BgSecond) { SmartSection(vm) }
        }

        Spacer(Modifier.height(24.dp))

        // Tampilkan feedback API, termasuk pesan error saat offline
//        Text(vm.responseText, color = AccentPurple)
    }
}

/*===========================================
=                 CARD WRAPPER              =
===========================================*/

@Composable
fun ModeCard(bg: Color, content: @Composable ColumnScope.() -> Unit) {
    Surface(
        color = bg,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) { content() }
    }
}

/*===========================================
=               MANUAL MODE UI              =
===========================================*/

@Composable
fun ManualSection(vm: ModeViewModel) {

//    Text("Manual Control", color = AccentGreen)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // 🌟 KOREKSI: Mengganti Column yang salah dengan Box
        Box(

            modifier = Modifier
                .size(48.dp) // Ukuran Box (lingkaran luar)
                // Menerapkan warna latar belakang dan bentuk lingkaran (BgRed)
                .background(
                    color = BgGreen,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center // Memastikan ikon berada di tengah Box,
        ) {
            // Konten: Ikon
            Icon(
                painter = painterResource(id = R.drawable.history),
                contentDescription = "Mode Desc",
                modifier = Modifier.size(24.dp), // Ukuran ikon di dalam Box
                tint = AccentGreen // 🌟 Menerapkan warna aksen ke ikon
            )
        }

        Spacer(modifier = Modifier.width(16.dp)) // Memberi jarak antara ikon dan teks

        // Column untuk menampung Dua Teks (mengisi sisa ruang)
        Column(
            modifier = Modifier.weight(1f) // Tetap menggunakan weight(1f)
        ) {
            // Title
            Text(
                text = "Timer Settings",
                color = Color.White,
                style = Typography.labelLarge
            )

            // Description
            Text(
                text = "Configure Duration",
                color = Color.Gray,
                style = Typography.labelSmall
            )
        }
    }
    Spacer(Modifier.height(12.dp))

    DarkInputField(
        value = vm.manualDurationText,
        label = "Durasi (detik)",
        onChange = {
            vm.manualDurationText = it
            vm.manualDuration = it.toIntOrNull() ?: 0
        }
    )

    Spacer(Modifier.height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth() // Pastikan Row mengisi lebar penuh
    ) {
        // Tombol 1: BUKA
        Button(
            onClick = {
                vm.updateMode(
                    ModeRequest(
                        mode = "manual",
                        servo = "open",
                        duration = vm.manualDuration
                    )
                )
            },
            // ⭐ PENTING: Menggunakan weight(1f) agar tombol mengisi setengah ruang
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = AccentGreen),
            shape = RoundedCornerShape(30)
        ) {
            Text("Open", color = Color.White)
        }

        Spacer(Modifier.width(16.dp)) // Jarak 16.dp di antara kedua tombol

        // Tombol 2: TUTUP
        Button(
            onClick = {
                vm.updateMode(
                    ModeRequest(
                        mode = "manual",
                        servo = "close",
                        duration = vm.manualDuration
                    )
                )
            },
            // ⭐ PENTING: Menggunakan weight(1f) agar tombol mengisi setengah ruang
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = AccentRed),
            shape = RoundedCornerShape(30)
        ) {
            Text("Close", color = Color.White)
        }
    }
}

/*===========================================
=                SMART MODE UI              =
===========================================*/

@Composable
fun SmartSection(vm: ModeViewModel) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // 🌟 KOREKSI: Mengganti Column yang salah dengan Box
        Box(

            modifier = Modifier
                .size(48.dp) // Ukuran Box (lingkaran luar)
                // Menerapkan warna latar belakang dan bentuk lingkaran (BgRed)
                .background(
                    color = BgGreen,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center // Memastikan ikon berada di tengah Box,
        ) {
            // Konten: Ikon
            Icon(
                painter = painterResource(id = R.drawable.history),
                contentDescription = "Mode Desc",
                modifier = Modifier.size(24.dp), // Ukuran ikon di dalam Box
                tint = AccentGreen // 🌟 Menerapkan warna aksen ke ikon
            )
        }

        Spacer(modifier = Modifier.width(16.dp)) // Memberi jarak antara ikon dan teks

        // Column untuk menampung Dua Teks (mengisi sisa ruang)
        Column(
            modifier = Modifier.weight(1f) // Tetap menggunakan weight(1f)
        ) {
            // Title
            Text(
                text = "Timer Settings",
                color = Color.White,
                style = Typography.labelLarge
            )

            // Description
            Text(
                text = "Configure Duration",
                color = Color.Gray,
                style = Typography.labelSmall
            )
        }
    }

    Spacer(Modifier.height(12.dp))


    DarkInputField(
        value = vm.smartIdleText,
        label = "Idle",
        onChange = {
            vm.smartIdleText = it
            vm.smartIdle = it.toIntOrNull() ?: 0
        }
    )

    Spacer(Modifier.height(12.dp))

    DarkInputField(
        value = vm.smartSleepText,
        label = "Sleep",
        onChange = {
            vm.smartSleepText = it
            vm.smartSleep = it.toIntOrNull() ?: 0
        }
    )

    Spacer(Modifier.height(12.dp))

    DarkInputField(
        value = vm.smartDurationText,
        label = "Duration",
        onChange = {
            vm.smartDurationText = it
            vm.smartDuration = it.toIntOrNull() ?: 0
        }
    )

    Spacer(Modifier.height(16.dp))

    Button(
        onClick = {
            vm.updateMode(
                ModeRequest(
                    mode = "smart",
                    idle = vm.smartIdle,
                    sleep = vm.smartSleep,
                    duration = vm.smartDuration
                )
            )
        },
        modifier = Modifier.fillMaxWidth(),
        // 1. Ubah warna menjadi hijau custom
        colors = ButtonDefaults.buttonColors(AccentGreen),
        // 2. Terapkan bentuk dengan sudut yang sangat membulat
        shape = RoundedCornerShape(20) // Nilai 50 atau yang serupa akan memberikan bentuk pil/kapsul
    ) {
        // Gunakan Row untuk menempatkan ikon dan teks secara horizontal
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp) // Sesuaikan padding vertikal jika perlu
        ) {
            Icon(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = "Apply Settings",
                modifier = Modifier
                    .size(25.dp)
                    .padding(end = 8.dp), // Ukuran ikon di dalam Box
                tint = AccentWhite // 🌟 Menerapkan warna aksen ke ikon
            )
            // Teks
            Text("Apply Settings", color = Color.White)
        }
    }
}

/*===========================================
=              DARK INPUT FIELD             =
===========================================*/

@Composable
fun DarkInputField(
    value: String,
    label: String,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label, color = AccentGreen) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = BgGreen,
            unfocusedContainerColor = BgGreen,
            cursorColor = AccentGreen,
            focusedBorderColor = AccentGreen,
            unfocusedBorderColor = AccentGreen.copy(alpha = 0.5f),
            focusedLabelColor = AccentGreen,
            unfocusedLabelColor = AccentGreen,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )
}