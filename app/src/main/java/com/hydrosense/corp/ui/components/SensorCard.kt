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
fun SensorCard(
    time: String,
    soil: Int,
    humidity: Int,
    temperature: Double,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(modifier = Modifier.padding(20.dp)) {

            // Timestamp
            Text(
                text = time,
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color(0xFF2E2E2E))
            Spacer(modifier = Modifier.height(16.dp))

            SensorIcon(
                iconRes = R.drawable.water,
                label = "Soil Moisture",
                value = "$soil°",
                valueColor = AccentRed,
                iconTint = AccentRed,
                iconBg = BgRed
            )

            Spacer(modifier = Modifier.height(14.dp))

            SensorIcon(
                iconRes = R.drawable.humidity,
                label = "Humidity",
                value = "$humidity%",
                valueColor = AccentBlue,
                iconTint = AccentBlue,
                iconBg = BgBlue

            )

            Spacer(modifier = Modifier.height(14.dp))

            SensorIcon(
                iconRes = R.drawable.thermometer,
                label = "Temperature",
                value = "${temperature}°C",
                valueColor = AccentGreen,
                iconTint = AccentGreen,
                iconBg = BgGreen
            )
        }
    }
}

@Composable
fun SensorIcon(
    iconRes: Int,
    label: String,
    value: String,
    valueColor: Color,
    iconTint: Color,
    iconBg: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .background(color = iconBg, shape = CircleShape)
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(iconTint)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = label,
                color = valueColor,
                fontSize = 16.sp
            )
        }

        Text(
            text = value,
            color = valueColor,
            fontSize = 16.sp
        )
    }
}

