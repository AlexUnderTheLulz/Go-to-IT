package com.example.gotoit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gotoit.Presentation.EventsScreen.EventsPage
import com.example.gotoit.Presentation.EventsScreen.EventsViewModel
import com.example.gotoit.Presentation.MainScreen


class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val eventsViewModel = ViewModelProvider(this)[EventsViewModel::class.java]

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "homeScreen", builder = {
                composable("homeScreen"){
                    MainScreen(navController)
                }
                composable("EventsScreen"){
                    EventsPage(viewModel = eventsViewModel, navController)
                }
            })
        }
    }
}

