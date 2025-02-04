package com.example.gotoit.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val baseUrl = "https://functions.yandexcloud.net";

    private fun getInstance(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val eventsApi: EventAPI = getInstance().create(EventAPI::class.java)
}
