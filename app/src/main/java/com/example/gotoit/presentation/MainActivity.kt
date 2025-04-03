package com.example.gotoit.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.gotoit.presentation.appnavigation.AppNavigation
import com.example.gotoit.presentation.viewmodel.EventsViewModel
import com.yandex.mapkit.MapKitFactory


class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey("4d35f8bf-05a8-4bc3-9e09-d60f2e191c21")
        MapKitFactory.initialize(this)

        enableEdgeToEdge()

        setContent {
            val eventsViewModel = ViewModelProvider(this)[EventsViewModel::class.java]
            AppNavigation(eventsViewModel)
        }
    }
}

