package com.hydrosense.corp.ui.screen.home

import com.hydrosense.corp.ui.screen.mode.*
import com.hydrosense.corp.ui.screen.chart.*
import com.hydrosense.corp.ui.screen.setting.*
import com.hydrosense.corp.ui.screen.history.*

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
import com.hydrosense.corp.ui.theme.HydroSenseTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HydroSenseTheme {
                var currentScreen by remember { mutableStateOf("Home") }

                Scaffold(
                    bottomBar = {
                        BottomBar(
                            currentScreen = "Home",
                            onTabSelected = { screen ->
                                currentScreen = screen
                                when (screen) {
                                    "Home" -> {}
                                    "History" -> startActivity(Intent(this, HistoryActivity::class.java))
                                    "Mode" -> startActivity(Intent(this, ModeActivity::class.java))
                                    "Chart" -> startActivity(Intent(this, ChartActivity::class.java))
                                    "Settings" -> startActivity(Intent(this, SettingActivity::class.java))
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(BgMain)
                            .padding(paddingValues)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HomeScreen()
                    }
                }
            }
        }
    }
}