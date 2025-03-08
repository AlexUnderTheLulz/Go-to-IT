package com.example.gotoit.presentation.appnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem (val route: String, val icon: ImageVector, val label: String) {
    object Events: BottomNavItem("EventsScreen", Icons.Default.Info, "События")
    object Calendar: BottomNavItem("calendarScreen", Icons.Default.DateRange, "Календарь")
    object Home: BottomNavItem("homeScreen", Icons.Default.AccountCircle, "Новости")
}