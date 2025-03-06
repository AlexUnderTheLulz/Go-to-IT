package com.example.gotoit.data.api

import com.example.gotoit.data.model.EventsModel
import retrofit2.Response
import retrofit2.http.GET

interface EventAPI {

    @GET("/d4es4b314ol9padu8q4q")
    suspend fun getEvents(

    ):Response<EventsModel>

}