package com.example.gotoit.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gotoit.presentation.viewmodel.EventsViewModel
import com.example.gotoit.R
import com.example.gotoit.presentation.theme.typography.Bold15
import com.example.gotoit.presentation.theme.typography.Bold24
import com.example.gotoit.presentation.theme.icons.vectorImages.Student


@Composable
fun MainScreen(){

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(5f)
                .background(color = colorResource(R.color.tags_green)),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                imageVector = Student,
                contentDescription = null,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
                .background(color = colorResource(R.color.background_black)),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(10.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .border(1.dp, color = Color.White, shape = RoundedCornerShape(10.dp))
                ) {
                    Bold24(modifier = Modifier.padding(10.dp), text = { "События" })
                    Bold15(modifier = Modifier.padding(10.dp), text = { "Митапы, конференции и просто неформальные встречи - все эти события из мира IT вы уже можете найти здесь" })
                }

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(10.dp)
                            .border(
                                1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {
                        Bold24(modifier = Modifier.padding(10.dp), text = { "Хакатоны" })
                        Bold15(
                            modifier = Modifier.padding(10.dp),
                            text = { "Здесь будут анонсы хакатонов" }
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(10.dp)
                            .border(
                                1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {
                        Bold24(modifier = Modifier.padding(10.dp), text = { "Новости" })
                        Bold15(modifier = Modifier.padding(10.dp), text = { "Здесь будут новости из мира IT" })
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview(){
    MainScreen()
}