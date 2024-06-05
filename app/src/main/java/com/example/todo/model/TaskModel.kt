package com.example.todo.model

import com.example.todo.data.local.TaskEntity
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class TaskModel(
    val title: String,
    val description: String,
    val time: Long
)

//mapper
fun TaskEntity.toTaskModel() :TaskModel{
    return TaskModel(
        title = this.title,
        description = this.description,
        time = this.time
    )
}

fun TaskModel.toTaskEntity() :TaskEntity {
    return TaskEntity(
        title = this.title,
        description = this.description,
        time = this.time
    )
}

fun Long.toTime(time:Long) : LocalDateTime {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(time),ZoneId.systemDefault())
}