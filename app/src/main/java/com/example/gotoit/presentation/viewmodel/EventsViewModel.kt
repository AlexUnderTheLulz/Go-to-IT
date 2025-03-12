package com.example.gotoit.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotoit.data.model.EventsModel
import com.example.gotoit.data.api.NetworkResponse
import com.example.gotoit.data.api.RetrofitInstance
import com.example.gotoit.data.model.EventsModelItem
import kotlinx.coroutines.launch


class EventsViewModel: ViewModel() {

    private val eventsApi = RetrofitInstance.eventsApi
    private val _eventsResult = MutableLiveData<NetworkResponse<EventsModel>>()
    val eventsResult : LiveData<NetworkResponse<EventsModel>> = _eventsResult

    private val _events = mutableStateOf<List<EventsModelItem>>(emptyList())

    private val _filteredEvents = mutableStateOf<List<EventsModelItem>>(emptyList())
    val filteredEvents: State<List<EventsModelItem>> = _filteredEvents

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    fun getData(){
        viewModelScope.launch {
            try {
                val response = eventsApi.getEvents()
                if (response.isSuccessful) {
                    response.body()?.let { eventsModel ->
                        _eventsResult.value = NetworkResponse.Success(eventsModel)

                        _events.value = eventsModel

                        _filteredEvents.value = eventsModel
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

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        filterEvents(query)
    }

    private fun filterEvents(query: String){
        _filteredEvents.value = if (query.isEmpty()){
            _events.value
        } else {
            _events.value.filter { event ->
                event.eventName.contains(query, ignoreCase = true)
            }
        }
    }
}