package com.example.todo.utils.alarmManager

import java.time.LocalDateTime

data class AlarmItem(
    val time: LocalDateTime,
    val title: String,
    val description: String
)