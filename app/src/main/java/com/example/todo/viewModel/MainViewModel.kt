package com.example.todo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.local.TaskEntity
import com.example.todo.model.TaskModel
import com.example.todo.repo.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: TasksRepository) :ViewModel(){
    private val _tasks = MutableStateFlow<List<TaskModel>>(emptyList())
    val tasks: StateFlow<List<TaskModel>> = _tasks

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
}