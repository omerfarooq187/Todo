package com.example.todo.model

import com.example.todo.data.local.TaskEntity

data class TaskModel(
    val id: Int,
    val title: String,
    val description: String,
)

//mapper
fun TaskEntity.toTaskModel() :TaskModel{
    return TaskModel(
        id = this.id,
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