package com.hydrosense.corp.ui.screen.mode

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
            style = Typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Manage IoT Settings",
            color = Color.Gray,
            style = Typography.labelLarge
        )

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(BgSecond)
                .padding(10.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(

                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = BgGreen,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.electric),
                            contentDescription = "Mode Desc",
                            modifier = Modifier.size(24.dp),
                            tint = AccentGreen
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Operation Mode",
                            color = Color.White,
                            style = Typography.labelLarge
                        )

                        Text(
                            text = "Choose Mode Between Auto & Manual",
                            color = Color.Gray,
                            style = Typography.labelSmall
                        )
                    }
                }

                // Toogle Switch
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Manual", color = AccentGreen)

                    Switch(
                        checked = vm.currentMode == "smart",
                        onCheckedChange = { isChecked ->
                            val newMode = if (isChecked) "smart" else "manual"

                            // Perubahan state lokal (vm.currentMode)
                            // agar UI (switch dan card) segera berubah.
                            vm.currentMode = newMode

                            // Rikuest API di latar belakang
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

        // Mode Card
        if (vm.currentMode == "manual") {
            ModeCard(bg = BgSecond) { ManualSection(vm) }
        } else {
            ModeCard(bg = BgSecond) { SmartSection(vm) }
        }

        Spacer(Modifier.height(24.dp))
        // Text(vm.responseText, color = AccentPurple)
    }
}

// Card Wrapper
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

// Card Mode Manual
@Composable
fun ManualSection(vm: ModeViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(

            modifier = Modifier
                .size(48.dp)
                .background(
                    color = BgGreen,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.history),
                contentDescription = "Mode Desc",
                modifier = Modifier.size(24.dp),
                tint = AccentGreen
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Column untuk menampung 2 Ruang
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Timer Settings",
                color = Color.White,
                style = Typography.labelLarge
            )

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
        modifier = Modifier.fillMaxWidth()
    ) {
        // Open Button
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
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = AccentGreen),
            shape = RoundedCornerShape(30)
        ) {
            Text("Open", color = Color.White)
        }

        Spacer(Modifier.width(16.dp))

        // Close Button
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
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = AccentRed),
            shape = RoundedCornerShape(30)
        ) {
            Text("Close", color = Color.White)
        }
    }
}



// UI Smart mode
@Composable
fun SmartSection(vm: ModeViewModel) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(

            modifier = Modifier
                .size(48.dp)
                .background(
                    color = BgGreen,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.history),
                contentDescription = "Mode Desc",
                modifier = Modifier.size(24.dp),
                tint = AccentGreen
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Timer Settings",
                color = Color.White,
                style = Typography.labelLarge
            )

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
        colors = ButtonDefaults.buttonColors(AccentGreen),
        shape = RoundedCornerShape(20)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = "Apply Settings",
                modifier = Modifier
                    .size(25.dp)
                    .padding(end = 8.dp),
                tint = AccentWhite
            )

            Text("Apply Settings", color = Color.White)
        }
    }
}



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