package com.example.todo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity (
    val title:String,
    val description:String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)