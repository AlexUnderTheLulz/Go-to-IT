package com.example.gotoit.presentation.appnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavItem (val route: String, val icon: ImageVector, val label: String) {
    object Events: BottomNavItem("EventsScreen", com.example.gotoit.presentation.theme.icons.vectorImages.Events, "События")
    object Calendar: BottomNavItem("calendarScreen", Icons.Default.DateRange, "Календарь")
    object Home: BottomNavItem("homeScreen", com.example.gotoit.presentation.theme.icons.vectorImages.News, "Новости")
}