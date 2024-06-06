package com.example.todo.utils.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var taskNotification: TaskNotification
    override fun onReceive(context: Context?, intent: Intent?) {
        val title = intent?.getStringExtra("TITLE") ?: return
        val description = intent?.getStringExtra("DESCRIPTION") ?: return
        taskNotification.sendNotification(
            title,
            description
        )
        Log.d("AlarmReceiver", "onReceive: $title")
    }
}