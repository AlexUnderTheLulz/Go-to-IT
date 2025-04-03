package com.example.gotoit.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface EventsDao {
    @Query("SELECT * FROM LikedEvents")
    suspend fun getAllLikedEvents(): List<LikedEvents>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikedEvent(event: LikedEvents)

    @Delete
    suspend fun deleteLikedEvent(event: String)
}