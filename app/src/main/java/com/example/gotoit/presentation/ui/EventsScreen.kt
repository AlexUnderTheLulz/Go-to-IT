package com.example.gotoit.presentation.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.gotoit.data.model.EventsModel
import com.example.gotoit.data.model.EventsModelItem
import com.example.gotoit.data.api.NetworkResponse
import com.example.gotoit.R
import com.example.gotoit.presentation.theme.typography.Bold15
import com.example.gotoit.presentation.theme.typography.Bold24
import com.example.gotoit.presentation.theme.typography.SemiBold13
import com.example.gotoit.presentation.viewmodel.EventsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun EventsPage(viewModel: EventsViewModel) {

    val eventsResult = viewModel.eventsResult.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background_black)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .weight(0.5f)
        ) {}

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Bold24(modifier = Modifier, text = { "События" })
        }

        Column(modifier = Modifier.weight(8f)) {
            when (val result = eventsResult.value) {
                is NetworkResponse.Error ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        Arrangement.Center,
                        Alignment.CenterHorizontally
                    ) {
                        Bold15(modifier = Modifier, text = { "Ошибка соединения\nс сервером" })
                    }
                NetworkResponse.Loading ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        Arrangement.Center,
                        Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = Color.White
                        )
                    }

                is NetworkResponse.Success -> EventList(
                    data = result.data
                )

                null ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        Arrangement.Center,
                        Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = Color.White
                        )
                    }
            }
        }
    }
}


@Composable
fun EventList(
    data: EventsModel,
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            isRefreshing = true

            EventsViewModel().getData()

            kotlinx.coroutines.MainScope().launch {
                delay(2000)
                isRefreshing = false
            }
        },
        indicator = { state, refreshTrigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = refreshTrigger,
                scale = true,
                backgroundColor = colorResource(R.color.background_black),
                contentColor = colorResource(R.color.tags_green)
            )
        }
    ) {
        LazyColumn {
            items(data) { item ->
                EventsItem(item)
            }
        }
    }
}

@Composable
fun EventsItem(item: EventsModelItem) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(item.link)) }

    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(229.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .clickable {
                context.startActivity(intent)
            }
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = item.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.75f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                SemiBold13(text = { item.eventDatetime })
                Bold24(modifier = Modifier.padding(top = 10.dp), text = { item.eventName })
            }
            EventsTags(eventsTags = item)
        }
    }
}

@Composable
fun EventsTags(eventsTags: EventsModelItem) {
    LazyRow {
        items(eventsTags.eventsTags) { tag ->
            Row(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 10.dp, 10.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = colorResource(R.color.tags_green))
            ) {
                SemiBold13(modifier = Modifier.padding(5.dp), text = { tag })
            }
        }
    }
}


@Preview
@Composable
fun EventsItemPreview() {
    EventsItem(
        EventsModelItem(
            "Test DateTime",
            "Test Name",
            listOf("Test Tag", "Test Tag"),
            "",
            ""
        )
    )
}