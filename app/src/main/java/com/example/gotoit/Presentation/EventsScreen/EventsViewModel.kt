package com.example.gotoit.Presentation.EventsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotoit.API.EventsModel
import com.example.gotoit.API.NetworkResponse
import com.example.gotoit.API.RetrofitInstance
import kotlinx.coroutines.launch


class EventsViewModel: ViewModel() {

    private val eventsApi = RetrofitInstance.eventsApi
    private val _eventsResult = MutableLiveData<NetworkResponse<EventsModel>>()
    val evetsResult : LiveData<NetworkResponse<EventsModel>> = _eventsResult

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
}