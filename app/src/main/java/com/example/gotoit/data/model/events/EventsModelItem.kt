package com.example.gotoit.data.model.events

data class EventsModelItem(
    val id: Int,
    val eventDatetime: String,
    val eventName: String,
    val eventsTags: List<String>,
    val link: String,
    val imageUrl: String,
    val city: String,
    var isLiked: Boolean = false
)