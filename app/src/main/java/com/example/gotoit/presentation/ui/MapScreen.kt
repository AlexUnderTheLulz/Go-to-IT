package com.example.gotoit.presentation.ui

import android.graphics.Color
import android.graphics.PointF
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gotoit.R
import com.example.gotoit.data.api.NetworkResponse
import com.example.gotoit.data.model.events.EventsModelItem
import com.example.gotoit.presentation.viewmodel.EventsViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider


@Composable
fun MainScreen(viewModel: EventsViewModel = viewModel()) {

    val context = LocalContext.current
    val appContext = context.applicationContext
    val mapView = remember { MapView(context) }
    val eventsResult by viewModel.eventsResult.observeAsState()

    LaunchedEffect(Unit) {
        MapKitFactory.initialize(context)
    }

    DisposableEffect(mapView) {
        mapView.onStart()
        MapKitFactory.getInstance().onStart()

        onDispose {
            mapView.onStop()
            MapKitFactory.getInstance().onStop()
        }
    }

    when (eventsResult) {
        is NetworkResponse.Loading -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
        is NetworkResponse.Error -> {
            Box(Modifier.fillMaxSize()) {
                Text(
                    text = "Ошибка загрузки данных",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        else -> {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { mapView },
                update = { view ->
                    val darkStyle = """
                        [
                            {
                                "tags": {
                                    "all": ["land"]
                                },
                                "elements": ["geometry"],
                                "stylers": {
                                    "color": "#1A1A1A"
                                }
                            },
                            {
                                "tags": {
                                    "all": ["water"]
                                },
                                "elements": ["geometry"],
                                "stylers": {
                                    "color": "#2A2A2A"
                                }
                            },
                            {
                                "tags": {
                                    "all": ["road"]
                                },
                                "elements": ["geometry"],
                                "stylers": {
                                    "color": "#3A3A3A"
                                }
                            },
                            {
                                "tags": {
                                    "all": ["landscape"]
                                },
                                "elements": ["geometry"],
                                "stylers": {
                                    "color": "#2F2F2F"
                                }
                            },
                            {
                                "tags": {
                                    "all": ["poi"]
                                },
                                "elements": ["geometry"],
                                "stylers": {
                                    "color": "#4A4A4A"
                                }
                            },
                            {
                                "tags": {
                                    "all": ["admin"]
                                },
                                "elements": ["geometry"],
                                "stylers": {
                                    "color": "#5A5A5A"
                                }
                            },
                            {
                                "tags": {
                                    "all": ["road"]
                                },
                                "elements": ["label.text.fill"],
                                "stylers": {
                                    "color": "#CCCCCC"
                                }
                            },
                            {
                                "tags": {
                                    "all": ["admin", "locality"]
                                },
                                "elements": ["label.text.fill"],
                                "stylers": {
                                    "color": "#CCCCCC"
                                }
                            },
                            {
                                "tags": {
                                    "all": ["admin", "locality"]
                                },
                                "elements": ["label.text.outline"],
                                "stylers": {
                                    "color": "#CCCCCC",
                                    "opacity": 0
                                }
                            },
                            {
                                "tags": {
                                    "all": ["poi"]
                                },
                                "elements": ["label.text.fill"],
                                "stylers": {
                                    "color": "#CCCCCC"
                                }
                            },
                            {
                                "tags": {
                                    "all": ["landscape"]
                                },
                                "elements": ["label.text.fill"],
                                "stylers": {
                                    "color": "#CCCCCC"
                                }
                            },
                           
                            {
                                "tags": {
                                    "all": ["water"]
                                },
                                "elements": ["label"],
                                "stylers": {
                                    "visibility": "off"
                                }
                            },
                            {
                                "elements": ["geometry"],
                                "stylers": {
                                    "saturation": -0.5,
                                    "lightness": -0.3
                                }
                            }
                                
                            
                        ]
                    """.trimIndent()
                    view.map.setMapStyle(darkStyle)

                    val eventsByCoordinates = viewModel.getEventsByCoordinates()

                    eventsByCoordinates.forEach { (point, eventsAtPoint) ->

                        val placemark = view.map.mapObjects.addPlacemark(point)

                        val citiesAtPoint = eventsAtPoint.map { it.city }.distinct()
                        val citiesText = citiesAtPoint.joinToString(", ")

                        placemark.apply {
                            opacity = 1f

                            val imageProvider = ImageProvider.fromResource(appContext, R.drawable.circle)
                            setIcon(imageProvider)

                            setIconStyle(
                                IconStyle().apply {
                                    scale = 0.1f
                                    anchor = PointF(0.5f, 1.0f)
                                }
                            )

                             setText("${eventsAtPoint.size}")
                             setTextStyle(
                                 TextStyle().apply {
                                     size = 12f
                                     color = Color.WHITE
                                     offset = 4f
                                     placement = TextStyle.Placement.CENTER
                                 }
                             )
                            userData = eventsAtPoint
                        }

                        placemark.addTapListener { mapObject, _ ->
                            val eventsData = mapObject.userData as? List<EventsModelItem>
                            eventsData?.let {
                                Toast.makeText(
                                    context,
                                    "$citiesText: ${it.size} мероприятий",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            true
                        }
                    }

                    view.map.move(
                        CameraPosition(
                            Point(55.751574, 37.573856), // Москва
                            5.0f,
                            0.0f,
                            0.0f
                        )
                    )
                }
            )
        }
    }
}