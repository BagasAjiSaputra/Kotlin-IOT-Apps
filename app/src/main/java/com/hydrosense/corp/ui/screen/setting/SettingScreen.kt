package com.hydrosense.corp.ui.screen.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hydrosense.corp.ui.screen.setting.SettingViewModel

@Composable
fun SettingScreen(vm: SettingViewModel = viewModel()) {

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            // Input PIN
            OutlinedTextField(
                value = vm.pin,
                onValueChange = { vm.pin = it },
                label = { Text("PIN") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input Recovery Code
            OutlinedTextField(
                value = vm.recovery,
                onValueChange = { vm.recovery = it },
                label = { Text("Recovery Code") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { vm.sendToApi() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan PIN & Recovery")
            }



            /* =============================
               ========== BASE URL =========
               ============================= */

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Konfigurasi Base URL",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = vm.baseUrl,
                onValueChange = { vm.baseUrl = it },
                label = { Text("Masukkan Base URL / IP") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { vm.saveBaseUrl() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan Base URL")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Response text
            if (vm.responseText.isNotEmpty()) {
                Text(
                    text = vm.responseText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
