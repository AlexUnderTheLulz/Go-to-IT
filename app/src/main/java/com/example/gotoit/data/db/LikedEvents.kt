package com.example.gotoit.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LikedEvents(
    @PrimaryKey val id: Int,
    val eventDatetime: String,
    val eventName: String,
    val eventsTags: List<String>,
    val link: String,
    val imageUrl: String,
)

