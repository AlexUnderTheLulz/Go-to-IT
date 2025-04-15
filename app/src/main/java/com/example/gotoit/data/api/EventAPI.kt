package com.example.gotoit.data.api

import com.example.gotoit.BuildConfig
import com.example.gotoit.data.model.events.EventsModel
import retrofit2.Response
import retrofit2.http.GET

const val EVENTS_KEY  = BuildConfig.EVENTS_API_KEY

interface EventAPI {

    @GET(EVENTS_KEY)
    suspend fun getEvents(

    ):Response<EventsModel>

}