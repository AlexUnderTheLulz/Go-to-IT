package com.example.gotoit.presentation.appnavigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gotoit.presentation.ui.EventsPage
import com.example.gotoit.presentation.ui.MainScreen
import com.example.gotoit.presentation.viewmodel.EventsViewModel


@Composable
fun NavigationGraph(
    navController: NavHostController,
    eventsViewModel: EventsViewModel,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = "EventsScreen",
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = "homeScreen") {
            MainScreen(navController)
        }

        composable(route = "EventsScreen") {
            EventsPage(eventsViewModel, navController)
        }
    }
}


@Composable
fun AppNavigation(eventsViewModel: EventsViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        NavigationGraph(
            navController = navController,
            eventsViewModel = eventsViewModel,
            innerPadding = innerPadding
        )
    }
}

