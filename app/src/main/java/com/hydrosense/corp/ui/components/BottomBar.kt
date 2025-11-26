package com.hydrosense.corp.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hydrosense.corp.ui.theme.*
import com.hydrosense.corp.R


data class NavItem(val title: String, val iconRes: Int, val selectedColor: Color)

@Composable
fun BottomBar(
    currentScreen: String,
    onTabSelected: (String) -> Unit
) {
    val items = listOf(
        NavItem("Home", R.drawable.home, AccentGreen),
        NavItem("History", R.drawable.history, AccentRed),
        NavItem("Mode", R.drawable.mode, AccentPurple),
        NavItem("Chart", R.drawable.chart, AccentBlue),
        NavItem("Settings", R.drawable.settings, AccentRed)
    )

    NavigationBar(
        containerColor = Color.Transparent
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentScreen == item.title,
                onClick = { onTabSelected(item.title) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.title,
                        tint = if (currentScreen == item.title) item.selectedColor else Color.Gray,
                        modifier = Modifier.size(25.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (currentScreen == item.title) item.selectedColor else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = item.selectedColor,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = item.selectedColor,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }

}
