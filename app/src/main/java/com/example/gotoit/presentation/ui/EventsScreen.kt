package com.example.gotoit.presentation.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
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
import com.example.gotoit.data.model.events.EventsModelItem
import com.example.gotoit.data.api.NetworkResponse
import com.example.gotoit.R
import com.example.gotoit.presentation.theme.typography.Bold15
import com.example.gotoit.presentation.theme.typography.Bold24
import com.example.gotoit.presentation.theme.typography.SemiBold13
import com.example.gotoit.presentation.viewmodel.EventsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun EventsPage(viewModel: EventsViewModel) {

    val events by viewModel.filteredEvents
    val eventsResult = viewModel.eventsResult.observeAsState()
    val searchQuery by viewModel.searchQuery

    val tags by viewModel.tags
    val selectedTags by viewModel.selectedTags

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        Column(modifier = Modifier.weight(1.7f)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Bold24(modifier = Modifier, text = { "События" }, color = Color.White)
            }

            TextField(
                value = searchQuery ?: "",
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(shape = RoundedCornerShape(10.dp)),
                placeholder = {
                    Bold15(
                        modifier = Modifier,
                        text = { "Поиск" },
                        color = Color.Gray
                    )
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray
                    )
                },
                colors = androidx.compose.material3.TextFieldDefaults.colors(
                    unfocusedContainerColor = colorResource(R.color.search),
                    disabledContainerColor = colorResource(R.color.search),
                    focusedContainerColor = colorResource(R.color.search),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,

                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedLeadingIconColor = Color.White,
                    focusedLeadingIconColor = Color.White
                )
            )
        }

        Column(modifier = Modifier.weight(8f)) {

            when (eventsResult.value) {
                is NetworkResponse.Error ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        Arrangement.Center,
                        Alignment.CenterHorizontally
                    ) {
                        Bold15(modifier = Modifier, text = { "Ошибка соединения\nс сервером" })
                    }

                is NetworkResponse.Loading, null ->

                    Column() {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp, bottom = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(7){
                                ShimmerPlaceholderTagChip()
                            }
                        }

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(7) {
                                ShimmerPlaceholderCard()
                            }
                        }
                    }

                is NetworkResponse.Success -> {
                    if (events.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Bold15(text = { "Мероприятия не найдены" })
                        }
                    } else {

                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(tags) { tag ->
                                TagChip(
                                    tag = tag,
                                    isSelected = selectedTags.contains(tag),
                                    onTagClick = { viewModel.toggleTag(tag) }
                                )
                            }
                        }

                        EventList(data = events)
                    }
                }
            }
        }
    }
}

@Composable
fun EventList(
    data: List<EventsModelItem>,
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
                contentColor = colorResource(R.color.tag)
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

    var isLiked by remember { mutableStateOf(item.isLiked) }

    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .aspectRatio(12f / 7f)
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SemiBold13(text = { "${item.eventDatetime} ${item.city}" })

                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share Icon",
                            tint = Color.White

                        )

                        Icon(
                            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Like Icon",
                            tint = if (isLiked) Color.Red else Color.White,
                            modifier = Modifier
                                .clickable {
                                    isLiked = !isLiked
                                    item.isLiked = isLiked
                                    CoroutineScope(Dispatchers.IO).launch {
                                        if (isLiked){

                                        }
                                    }
                                }
                        )
                    }
                }

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

@Composable
fun TagChip(tag: String, isSelected: Boolean, onTagClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(
                color = if (isSelected) colorResource(R.color.tag).copy(alpha = 0.8f)
                else colorResource(R.color.tag).copy(alpha = 0.5f)
            )
            .clickable { onTagClick() }
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Bold15(
            text = { tag },
            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun ShimmerPlaceholderTagChip() {

    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(Color.Gray.copy(alpha = alpha))
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .width(40.dp)
            .height(15.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun ShimmerPlaceholderCard() {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Card(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .aspectRatio(12f / 7f)
            .background(Color.Gray.copy(alpha = alpha))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(10.dp))
                .background(Color.LightGray.copy(alpha = 0.3f))
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .height(13.dp)
                        .width(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Gray.copy(alpha = alpha), RoundedCornerShape(24.dp))
                )

                Box(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .height(24.dp)
                        .width(200.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color.Gray.copy(alpha = alpha), RoundedCornerShape(24.dp))
                )
            }

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(25.dp)
                        .width(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Gray.copy(alpha = alpha), RoundedCornerShape(24.dp))
                )

                Box(
                    modifier = Modifier
                        .height(25.dp)
                        .width(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Gray.copy(alpha = alpha), RoundedCornerShape(24.dp))
                )

                Box(
                    modifier = Modifier
                        .height(25.dp)
                        .width(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Gray.copy(alpha = alpha), RoundedCornerShape(24.dp))
                )
            }
        }
    }
}


@Preview
@Composable
fun EventsItemPreview() {
    EventsItem(
        EventsModelItem(
            1,
            "Test DateTime",
            "Test Name",
            listOf("Test Tag", "Test Tag"),
            "",
            "",
            "Moscow"
        )
    )
}

@Preview
@Composable
fun EventsPagePreview() {
    EventsPage(viewModel = EventsViewModel())
}