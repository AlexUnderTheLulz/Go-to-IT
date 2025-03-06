package com.example.gotoit.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotoit.data.model.EventsModel
import com.example.gotoit.data.api.NetworkResponse
import com.example.gotoit.data.api.RetrofitInstance
import kotlinx.coroutines.launch


class EventsViewModel: ViewModel() {

    private val eventsApi = RetrofitInstance.eventsApi
    private val _eventsResult = MutableLiveData<NetworkResponse<EventsModel>>()
    val eventsResult : LiveData<NetworkResponse<EventsModel>> = _eventsResult

    fun getData(){
        viewModelScope.launch {
            try {
                val response = eventsApi.getEvents()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _eventsResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _eventsResult.value = NetworkResponse.Error("Ошибка")
                }
            }
            catch (e: Exception){
                _eventsResult.value = NetworkResponse.Error("Ошибка")
            }
        }
    }

    init{
        getData()
    }
}