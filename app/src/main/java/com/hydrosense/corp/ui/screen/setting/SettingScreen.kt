package com.hydrosense.corp.ui.screen.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
// Import warna dari paket tema yang disediakan
import com.hydrosense.corp.ui.theme.*
import com.hydrosense.corp.ui.theme.AccentGreen // Bisa digunakan untuk warna tombol
import com.hydrosense.corp.ui.theme.AccentRed // Contoh warna lain
import com.hydrosense.corp.ui.theme.AccentBlue // Contoh warna lain



@Composable
fun SettingScreen(vm: SettingViewModel = viewModel()) {

    // Gunakan parameter containerColor pada Scaffold untuk mengatur warna latar belakang.
    // Kita gunakan BgMain untuk latar belakang utama yang gelap.
    Scaffold(
        containerColor = BgMain // Mengatur latar belakang Scaffold menjadi hitam/gelap
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
            // Tidak perlu .background(BgMain) lagi jika sudah diatur di Scaffold
            ,
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            // OutlinedTextField biasanya menyesuaikan secara otomatis jika MaterialTheme dalam Dark Mode.
            // Warna label dan outline akan menjadi putih/terang.

            // Input PIN
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BgPurple, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {

                Text(
                    text = "Keamanan",
                    color = AccentPurple,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Input PIN
                OutlinedTextField(
                    value = vm.pin,
                    onValueChange = { vm.pin = it },
                    label = { Text("PIN", color = AccentPurple) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = AccentPurple,
                        unfocusedTextColor = AccentPurple,
                        cursorColor = AccentPurple,
                        focusedBorderColor = AccentPurple,
                        unfocusedBorderColor = AccentPurple,
                        focusedLabelColor = AccentPurple,
                        unfocusedLabelColor = AccentPurple,
                        selectionColors = TextSelectionColors(
                            handleColor = AccentPurple,
                            backgroundColor = AccentPurple.copy(alpha = 0.3f)
                        )
                    )

                )

                Spacer(modifier = Modifier.height(16.dp))

                // Input Recovery Code
                OutlinedTextField(
                    value = vm.recovery,
                    onValueChange = { vm.recovery = it },
                    label = { Text("Recovery Code", color = AccentPurple) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = AccentPurple,
                        unfocusedTextColor = AccentPurple,
                        cursorColor = AccentPurple,
                        focusedBorderColor = AccentPurple,
                        unfocusedBorderColor = AccentPurple,
                        focusedLabelColor = AccentPurple,
                        unfocusedLabelColor = AccentPurple,
                        selectionColors = TextSelectionColors(
                            handleColor = AccentPurple,
                            backgroundColor = AccentPurple.copy(alpha = 0.3f)
                        )
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { vm.sendToApi() },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Save Changes",
                        color = AccentPurple
                    )
                }
            }



            /* =============================
               ========== BASE URL =========
               ============================= */

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BgPurple, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {

                Text(
                    text = "Base URL Config",
                    color = AccentPurple,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = vm.baseUrl,
                    onValueChange = { vm.baseUrl = it },
                    label = { Text("Base URL", color = AccentPurple) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = AccentPurple,
                        unfocusedTextColor = AccentPurple,
                        cursorColor = AccentPurple,
                        focusedBorderColor = AccentPurple,
                        unfocusedBorderColor = AccentPurple,
                        focusedLabelColor = AccentPurple,
                        unfocusedLabelColor = AccentPurple,
                        selectionColors = TextSelectionColors(
                            handleColor = AccentPurple,
                            backgroundColor = AccentPurple.copy(alpha = 0.3f)
                        )
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { vm.saveBaseUrl() },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)   // 🔥 bikin tombol rounded
                ) {
                    Text(
                        text = "Save URL",
                        color = AccentWhite
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))

                if (vm.responseText.isNotEmpty()) {
                    Text(
                        text = vm.responseText,
                        color = AccentPurple,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

// Catatan: Anda juga perlu memastikan bahwa file SettingViewModel (yang tidak Anda berikan) sudah diimpor.