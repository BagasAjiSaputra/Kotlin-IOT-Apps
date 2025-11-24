package com.hydrosense.corp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hydrosense.corp.R
import com.hydrosense.corp.ui.theme.*

@Composable
fun BigSensorSection(
    time: String,
    soil: Int,
    humidity: Int,
    temperature: Double
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        Text(
            text = time,
            fontSize = 14.sp,
            color = Color.Gray
        )

        HomeCard(
            iconRes = R.drawable.water,
            title = "Soil Moisture",
            value = "$soil%",
            iconTint = AccentRed,
            iconBg = BgRed,
            valueColor = AccentRed
        )

        HomeCard(
            iconRes = R.drawable.humidity,
            title = "Humidity",
            value = "$humidity%",
            iconTint = AccentBlue,
            iconBg = BgBlue,
            valueColor = AccentBlue
        )

        HomeCard(
            iconRes = R.drawable.thermometer,
            title = "Temperature",
            value = "${temperature}°C",
            iconTint = AccentGreen,
            iconBg = BgGreen,
            valueColor = AccentGreen
        )
    }
}

@Composable
fun HomeCard(
    iconRes: Int,
    title: String,
    value: String,
    iconTint: Color,
    iconBg: Color,
    valueColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Icon + circle background
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(60.dp)
                    .background(iconBg, CircleShape)
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = title,
                    modifier = Modifier.size(32.dp),
                    colorFilter = ColorFilter.tint(iconTint)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = value,
                    fontSize = 22.sp,
                    color = valueColor
                )
            }
        }
    }
}
