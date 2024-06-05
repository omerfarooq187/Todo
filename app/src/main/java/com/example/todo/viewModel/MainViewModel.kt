package com.example.todo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.model.TaskModel
import com.example.todo.repo.TasksRepository
import com.example.todo.utils.alarmManager.AlarmItem
import com.example.todo.utils.alarmManager.TaskAlarmScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TasksRepository,
    private val alarmScheduler: TaskAlarmScheduler
) :ViewModel(){
    private val _tasks = MutableStateFlow<List<TaskModel>>(emptyList())
    val tasks: StateFlow<List<TaskModel>> = _tasks

    //Database operations
    init {
        viewModelScope.launch {
            repository.getTasks().collect { tasksList->
                _tasks.value = tasksList
            }
        }
    }

    fun addTask(task: TaskModel) {
        viewModelScope.launch {
            repository.insertData(task)
        }
    }

    suspend fun deleteTask(task: TaskModel) {
        repository.deleteData(task)
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            repository.deleteAllData()
        }
    }

    //Alarm Scheduler operations
    fun setAlarm(alarmItem: AlarmItem) {
        alarmScheduler.schedule(alarmItem)
    }

    fun cancelAlarm(alarmItem: AlarmItem) {
        alarmScheduler.cancel(alarmItem)
    }
}