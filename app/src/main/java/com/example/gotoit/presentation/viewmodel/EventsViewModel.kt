package com.example.gotoit.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotoit.data.model.events.EventsModel
import com.example.gotoit.data.api.NetworkResponse
import com.example.gotoit.data.api.RetrofitInstance
import com.example.gotoit.data.model.events.EventsModelItem
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.launch


class EventsViewModel : ViewModel() {

    private val eventsApi = RetrofitInstance.eventsApi
    private val _eventsResult = MutableLiveData<NetworkResponse<EventsModel>>()
    val eventsResult: LiveData<NetworkResponse<EventsModel>> = _eventsResult

    private val _events = mutableStateOf<List<EventsModelItem>>(emptyList())
    val events: State<List<EventsModelItem>> = _events

    private val _tags = mutableStateOf<List<String>>(emptyList())
    val tags: State<List<String>> = _tags

    private val _selectedTags = mutableStateOf<Set<String>>(emptySet())
    val selectedTags: State<Set<String>> = _selectedTags

    private val _filteredEvents = mutableStateOf<List<EventsModelItem>>(emptyList())
    val filteredEvents: State<List<EventsModelItem>> = _filteredEvents

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val cityCoordinates = mapOf(
        "Москва" to Point(55.751574, 37.573856),
        "Санкт-Петербург" to Point(59.934280, 30.335098),
        "Новосибирск" to Point(55.008352, 82.935732),
        "Сочи" to Point(43.585472, 39.723062),
        "Томск" to Point(56.484679, 84.948174),
        "Пермь" to Point(58.010450, 56.229443),
        "Екатеринбург" to Point(56.838926, 60.605702),
        "Казань" to Point(55.830431, 49.066081),
        "Нижний Новгород" to Point(56.3287, 44.002),
        "Новосибирск" to Point(55.008352, 82.935732),
        "Иннополис" to Point(55.7517163, 48.7473099),
        "Ульяновск" to Point(54.3282, 48.3866)

        // Пополнить список городов
    )

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            try {
                val response = eventsApi.getEvents()
                if (response.isSuccessful) {
                    response.body()?.let { eventsModel ->
                        _eventsResult.value = NetworkResponse.Success(eventsModel)

                        _events.value = eventsModel

                        _tags.value = eventsModel.flatMap { it.eventsTags }.distinct()

                        filterEvents(_searchQuery.value)
                    }
                } else {
                    _eventsResult.value = NetworkResponse.Error("Ошибка")
                }
            } catch (e: Exception) {
                _eventsResult.value = NetworkResponse.Error("Ошибка")
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        filterEvents(query)
    }

    fun toggleTag(tag: String) {
        val currentTags = _selectedTags.value.toMutableSet()
        if (currentTags.contains(tag)) {
            currentTags.remove(tag)
        } else {
            currentTags.add(tag)
        }
        _selectedTags.value = currentTags

        filterEvents(_searchQuery.value)
    }

    private fun filterEvents(query: String) {
        val filteredBySearch = if (query.isEmpty()) {
            _events.value
        } else {
            _events.value.filter { event ->
                event.eventName.contains(query, ignoreCase = true)
            }
        }

        _filteredEvents.value = if (_selectedTags.value.isEmpty()) {

            filteredBySearch
        } else {

            filteredBySearch.filter { event ->
                event.eventsTags.any { tag -> _selectedTags.value.contains(tag) }
            }
        }
    }

    fun getCityPoint(city: String): Point {
        val point = cityCoordinates[city] ?: Point(55.751574, 37.573856) // Москва по умолчанию
        if (cityCoordinates[city] == null) {
            Log.w("MapDebug", "Город $city не найден в cityCoordinates, используется Москва по умолчанию")
        }
        return point
    }

    fun getEventsByCoordinates(): Map<Point, List<EventsModelItem>> {
        return _filteredEvents.value
            .filter { it.city.isNotEmpty() }
            .groupBy { getCityPoint(it.city) }
    }
}