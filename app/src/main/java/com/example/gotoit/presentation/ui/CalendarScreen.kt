package com.example.gotoit.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gotoit.R
import com.example.gotoit.data.model.calendar.CalendarDay
import com.example.gotoit.data.model.calendar.Event
import com.example.gotoit.presentation.theme.typography.Bold15
import com.example.gotoit.presentation.theme.typography.Bold24
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarPage(events: List<Event>) {

    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    val calendarDays = generateCalendarDays(currentMonth, events)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background_black)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(9f)
                .padding(15.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){

                Row {
                    Bold24(
                        text = {"<"},
                        modifier = Modifier
                            .clickable { currentMonth = currentMonth.minusMonths(1) }
                            .padding(8.dp)
                    )
                }

                Bold24(
                    text = { "${currentMonth.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale("ru"))} ${currentMonth.year}" }
                )

                Row {
                    Bold24(
                        text = {">"},
                        modifier = Modifier
                            .clickable { currentMonth = currentMonth.plusMonths(1) }
                            .padding(8.dp)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс").forEach { day ->
                    Bold15(
                        text = { day },
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                    )
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(calendarDays.size){ index ->
                    val day = calendarDays[index]

                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    day.hasEvent -> Color.Green.copy(alpha = 0.3f)
                                    !day.isCurrentMonth -> Color.Gray.copy(alpha = 0.1f)
                                    else -> Color.Transparent
                                }
                            )
                    ){
                        Bold15(
                            text = { day.date.dayOfMonth.toString() },
                            modifier = Modifier.align(Alignment.Center),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}


fun generateCalendarDays(
    yearMonth: YearMonth,
    events: List<Event>
): List<CalendarDay> {
    val days = mutableListOf<CalendarDay>()
    val firstDayOfMonth = yearMonth.atDay(1)
    val lastDayOfMonth = yearMonth.atEndOfMonth()

    // Смещение первого дня (0 = Понедельник, 6 = Воскресенье)
    val firstDayOffset = (firstDayOfMonth.dayOfWeek.value - 1 + 7) % 7 // Нормализуем к 0-6, где 0 = Пн
    val daysInMonth = yearMonth.lengthOfMonth()

    // Дни предыдущего месяца
    val prevMonth = yearMonth.minusMonths(1)
    val daysInPrevMonth = prevMonth.lengthOfMonth()
    for (i in (daysInPrevMonth - firstDayOffset + 1)..daysInPrevMonth) {
        days.add(CalendarDay(prevMonth.atDay(i), false))
    }

    // Дни текущего месяца
    for (day in 1..daysInMonth) {
        val date = yearMonth.atDay(day)
        val hasEvent = events.any { it.eventDate == date }
        days.add(CalendarDay(date, true, hasEvent))
    }

    // Дни следующего месяца (до конца недели)
    val nextMonth = yearMonth.plusMonths(1)
    val remainingDays = (7 - (days.size % 7)) % 7
    for (day in 1..remainingDays) {
        days.add(CalendarDay(nextMonth.atDay(day), false))
    }

    return days
}

@Preview(showBackground = true)
@Composable
fun CalendarPagePreview() {
    CalendarPage(events = emptyList())

}