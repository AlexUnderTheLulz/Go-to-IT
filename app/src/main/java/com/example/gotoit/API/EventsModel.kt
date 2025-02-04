package com.example.gotoit.API

class EventsModel : ArrayList<EventsModelItem>()

data class EventsModelItem(
    val eventDatetime: String,
    val eventName: String,
    val eventsTags: List<String>,
    val link: String
)