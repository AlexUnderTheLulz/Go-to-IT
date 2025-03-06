package com.example.gotoit.data.model

data class EventsModelItem(
    val eventDatetime: String,
    val eventName: String,
    val eventsTags: List<String>,
    val link: String,
    val imageUrl: String
)