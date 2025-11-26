package com.hydrosense.corp.ui.screen.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hydrosense.corp.ui.theme.HydroSenseTheme
import com.hydrosense.corp.MainActivity

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lambda function untuk navigasi setelah login berhasil
        val navigateToHome: () -> Unit = {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Hapus LoginActivity dari back stack
        }

        setContent {
            HydroSenseTheme {
                val vm: LoginViewModel = viewModel()
                LoginScreen(vm = vm, onLoginSuccess = navigateToHome)
            }
        }
    }
}