package com.example.gotoit.Presentation.EventsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gotoit.API.EventsModel
import com.example.gotoit.API.EventsModelItem
import com.example.gotoit.API.NetworkResponse
import com.example.gotoit.R
import com.example.gotoit.theme.typography.Bold15
import com.example.gotoit.theme.typography.Bold24
import com.example.gotoit.theme.typography.SemiBold13


@Composable
fun EventsPage(viewModel: EventsViewModel, navController: NavController){

    var events by remember {
        mutableStateOf("")
    }

    val eventsResult = viewModel.evetsResult.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background_black)),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Row(
            modifier = Modifier
                .weight(0.5f)
        ){}

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = {
                    navController.navigate("homeScreen")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    tint = Color.White,
                    contentDescription = null
                )
            }

            Bold24(modifier = Modifier, text = { "События" })

            IconButton(
                onClick = { viewModel.getData() }
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(8f)
        ) {
            when (val result = eventsResult.value) {
                is NetworkResponse.Error -> TODO()
                NetworkResponse.Loading ->
                    Column (
                        modifier = Modifier
                            .fillMaxSize(),
                        Arrangement.Center,
                        Alignment.CenterHorizontally
                    ){
                        CircularProgressIndicator(
                            color = Color.White
                        )
                    }
                is NetworkResponse.Success -> EventList(
                    data = result.data
                )

                null ->
                    Column (
                        modifier = Modifier
                            .fillMaxSize(),
                        Arrangement.Center,
                        Alignment.CenterHorizontally
                    ){
                        Bold15(text = { "Нажмите обновить, чтобы получить" })
                        Bold15(text = { "список актуальных мероприятий" })
                    }
            }
        }
    }
}

@Composable
fun EventList(data: EventsModel){
    LazyColumn{
        items(data){
            item -> EventsItem(item)
        }
    }
}

@Composable
fun EventsItem(item: EventsModelItem){
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(270.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = colorResource(R.color.tags_green)),

        verticalArrangement = Arrangement.SpaceBetween
    ){
        Column() {
            SemiBold13(modifier = Modifier.padding(10.dp), text = { item.eventDatetime })

            Bold24(modifier = Modifier.padding(10.dp), text = { item.eventName })
        }

        EventsTags(eventsTags = item)
    }
}

@Composable
fun EventsTags(eventsTags: EventsModelItem){
    LazyRow {
        items(eventsTags.eventsTags) { tag ->
            Row (
                modifier = Modifier
                    .padding(10.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = colorResource(R.color.tag_tags_green))
            ){
                SemiBold13(modifier = Modifier.padding(5.dp), text = { tag })
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun EventsPagePreview(){
    EventsPage(EventsViewModel())
}
 */

@Preview
@Composable
fun EventsItemPreview(){
    EventsItem(EventsModelItem(
        "Test DateTime",
        "Test Name",
        listOf("Test Tag", "Test Tag"),
        "")
    )
}