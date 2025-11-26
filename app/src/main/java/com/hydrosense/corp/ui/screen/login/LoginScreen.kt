package com.hydrosense.corp.ui.screen.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hydrosense.corp.ui.theme.*
import com.hydrosense.corp.ui.components.*

private const val MAX_PIN_LENGTH = 6

@Composable
fun LoginScreen(vm: LoginViewModel = viewModel(), onLoginSuccess: () -> Unit) {

    // State Mode
    var isRecoveryMode by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = BgMain
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = if (isRecoveryMode) "Recovery" else "PIN",
                style = MaterialTheme.typography.headlineMedium,
                color = AccentGreen,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(32.dp))


            // Numpad
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    //.background(BgPurple, shape = RoundedCornerShape(16.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (!isRecoveryMode) {
                    // Mode Login
                    PinDisplay(pin = vm.inputPin)
                    Spacer(modifier = Modifier.height(24.dp))

                    // Numpad
                    Numpad(
                        onNumberClick = { number ->
                            if (vm.inputPin.length < MAX_PIN_LENGTH) {
                                vm.inputPin += number
                            }
                        },
                        onDeleteClick = {
                            if (vm.inputPin.isNotEmpty()) {
                                vm.inputPin = vm.inputPin.dropLast(1)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                } else {
                    // Mode Rexovery
                    OutlinedTextField(
                        value = vm.inputRecoveryCode,
                        onValueChange = { vm.inputRecoveryCode = it },
                        label = { Text("Enter Recovery Code", color = AccentGreen) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = AccentWhite,
                            unfocusedTextColor = AccentWhite,
                            cursorColor = AccentGreen,
                            focusedBorderColor = AccentGreen,
                            unfocusedBorderColor = AccentGreen,
                            focusedLabelColor = AccentGreen,
                            unfocusedLabelColor = AccentGreen,
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // TButton Action utama
                Button(
                    onClick = {
                        if (!isRecoveryMode) {
                            vm.attemptLogin(onLoginSuccess)
                        } else {
                            vm.attemptRecoveryLogin(onLoginSuccess)
                        }
                    },

                    // Aktifkan jika PIN sudah penuh atau dalam mode Recovery
                    enabled = !vm.isLoading && (isRecoveryMode || vm.inputPin.length == MAX_PIN_LENGTH),
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BgGreen)
                ) {
                    if (vm.isLoading) {
                        CircularProgressIndicator(color = AccentGreen, modifier = Modifier.size(24.dp))
                    } else {
                        Text(
                            text = if (isRecoveryMode) "Verify Recovery" else "Login",
                            color = AccentGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (vm.responseMessage.isNotEmpty()) {
                    Text(
                        text = vm.responseMessage,
                        color = if (vm.loginSuccess) AccentGreen else AccentRed,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = if (isRecoveryMode) "Back to PIN Login" else "Forgot PIN? Use Recovery Code",
                color = AccentGreen,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .clickable {
                        isRecoveryMode = !isRecoveryMode
                        vm.responseMessage = ""
                        vm.inputPin = ""
                        vm.inputRecoveryCode = ""
                    }
                    .padding(8.dp)
            )
        }
    }
}