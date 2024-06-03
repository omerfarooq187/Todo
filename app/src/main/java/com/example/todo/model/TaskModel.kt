package com.example.todo.model

import com.example.todo.data.local.TaskEntity

data class TaskModel(
    val title: String,
    val description: String,
)

//mapper
fun TaskEntity.toTaskModel() :TaskModel{
    return TaskModel(
        title = this.title,
        description = this.description
    )
}

fun TaskModel.toTaskEntity() :TaskEntity {
    return TaskEntity(
        title = this.title,
        description = this.description
    )
}