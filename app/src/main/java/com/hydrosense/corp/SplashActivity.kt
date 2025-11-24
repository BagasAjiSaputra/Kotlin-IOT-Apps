package com.hydrosense.corp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // Load animasi Lottie dari res/raw
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fruit))

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 1, // play 1x
        restartOnPlay = false
    )

    // Pindah otomatis setelah animasi selesai
    LaunchedEffect(progress) {
        if (progress == 1f) {
            delay(200) // sedikit delay supaya smooth
            onTimeout()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(400.dp)
        )
    }
}
