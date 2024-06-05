package com.example.todo.utils.alarmManager

interface AlarmScheduler {
    fun schedule(item:AlarmItem)
    fun cancel(item: AlarmItem)
}