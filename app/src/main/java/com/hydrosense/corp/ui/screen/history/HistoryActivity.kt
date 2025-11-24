package com.hydrosense.corp.ui.screen.history

import com.hydrosense.corp.ui.screen.home.*
import com.hydrosense.corp.ui.screen.mode.*
import com.hydrosense.corp.ui.screen.history.*
import com.hydrosense.corp.ui.screen.chart.*
import com.hydrosense.corp.ui.screen.setting.*

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hydrosense.corp.ui.components.BottomBar
import com.hydrosense.corp.ui.theme.BgMain
import com.hydrosense.corp.ui.theme.*
import com.hydrosense.corp.data.repository.SensorRepository
import com.hydrosense.corp.data.remote.RetrofitInstance

class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi repository & viewmodel
        val repository = SensorRepository(RetrofitInstance.getApi(this))
        val viewModel = HistoryViewModel(repository)

        setContent {
            HydroSenseTheme {
                var currentScreen by remember { mutableStateOf("History") }

                Scaffold(
                    bottomBar = {
                        BottomBar(
                            currentScreen = "History",
                            onTabSelected = { screen ->
                                currentScreen = screen
                                when (screen) {
                                    "Home" -> startActivity(Intent(this, HomeActivity::class.java))
                                    "History" -> { /* tetap di halaman ini */
                                    }

                                    "Mode" -> startActivity(Intent(this, ModeActivity::class.java))
                                    "Chart" -> startActivity(
                                        Intent(
                                            this,
                                            ChartActivity::class.java
                                        )
                                    )

                                    "Settings" -> startActivity(
                                        Intent(
                                            this,
                                            SettingActivity::class.java
                                        )
                                    )
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    // Pass viewmodel ke HistoryScreen
                    HistoryScreen(
                        viewModel = viewModel,
                        modifier = Modifier
                            .background(BgMain)
                            .padding(paddingValues)
                    )
                }
            }
        }
    }
}
