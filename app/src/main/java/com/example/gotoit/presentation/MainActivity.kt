package com.example.gotoit.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.gotoit.BuildConfig
import com.example.gotoit.presentation.appnavigation.AppNavigation
import com.example.gotoit.presentation.viewmodel.EventsViewModel
import com.yandex.mapkit.MapKitFactory



class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapApiKey = BuildConfig.MAP_API_KEY

        MapKitFactory.setApiKey(mapApiKey)
        MapKitFactory.initialize(this)

        enableEdgeToEdge()

        setContent {
            val eventsViewModel = ViewModelProvider(this)[EventsViewModel::class.java]
            AppNavigation(eventsViewModel)
        }
    }
}

