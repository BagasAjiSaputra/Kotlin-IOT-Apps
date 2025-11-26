package com.hydrosense.corp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hydrosense.corp.ui.components.*
import com.hydrosense.corp.ui.theme.*

import com.hydrosense.corp.ui.screen.home.*
import com.hydrosense.corp.ui.screen.test.*
import com.hydrosense.corp.ui.screen.mode.*
import com.hydrosense.corp.ui.screen.chart.*
import com.hydrosense.corp.ui.screen.setting.*
import com.hydrosense.corp.ui.screen.history.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HydroSenseTheme {
                MainScreen()
            }
        }
    }

    @Composable
    fun MainScreen() {
        Scaffold(
            bottomBar = {
                BottomBar(
                    currentScreen = "Home",
                    onTabSelected = { screen ->
                        when (screen) {
                            "Home" -> startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                            "History" -> startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
                            "Mode" -> startActivity(Intent(this@MainActivity, ModeActivity::class.java))
                            "Chart" -> startActivity(Intent(this@MainActivity, ChartActivity::class.java))
                            "Settings" -> startActivity(Intent(this@MainActivity, SettingActivity::class.java))
                            "Test" -> startActivity(Intent(this@MainActivity, TestActivity::class.java))
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
                    .padding(16.dp)
            ) {
                HomeScreen()
            }
        }
    }
}
