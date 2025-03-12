package com.example.gotoit.data.model.calendar

import java.time.LocalDate

data class CalendarDay (
    val date: LocalDate,
    val isCurrentMonth: Boolean,
    val hasEvent: Boolean = false
)