package com.example.todo.repo

import com.example.todo.data.local.TaskDao
import com.example.todo.model.TaskModel
import com.example.todo.model.toTaskEntity
import com.example.todo.model.toTaskModel
import com.example.todo.utils.alarmManager.AlarmItem
import com.example.todo.utils.alarmManager.TaskAlarmScheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TasksRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val alarmScheduler: TaskAlarmScheduler
) {

    //Database operations
    fun getTasks(): Flow<List<TaskModel>> {
        return taskDao.getTasks().map { entities->
            entities.map {
                it.toTaskModel()
            }
        }
    }

    suspend fun insertData(taskModel: TaskModel) {
        taskDao.insertTask(taskModel.toTaskEntity())
    }

    suspend fun deleteData(taskModel: TaskModel) {
        taskDao.deleteTask(taskModel.toTaskEntity())
    }

    suspend fun deleteAllData() {
        taskDao.deleteAllTasks()
    }

    //Alarm Scheduling operations
    fun scheduleAlarm(alarmItem: AlarmItem) {
        alarmScheduler.schedule(alarmItem)
    }

    fun cancelAlarm(alarmItem: AlarmItem) {
        alarmScheduler.cancel(alarmItem)
    }
}