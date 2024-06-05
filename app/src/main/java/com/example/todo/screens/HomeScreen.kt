package com.example.todo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.todo.EditTaskScreenRoute
import com.example.todo.model.TaskModel
import com.example.todo.viewModel.MainViewModel
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

@Composable
fun HomeScreen(navController: NavController, mainViewModel: MainViewModel) {
    HomeScreenContents(navController, mainViewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContents(navController: NavController, mainViewModel: MainViewModel) {
    val lazyState = rememberLazyListState()
    val tasksList = mainViewModel.tasks.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Tasks",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            },
                actions = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable {
                                mainViewModel.deleteAllTasks()
                            }
                    )
                })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(
                    EditTaskScreenRoute(
                        null,
                        null,
                        System.currentTimeMillis()
                    )
                )
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
            }
        }
    ) { innerPadding->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (tasksList.value.isEmpty()) {
                Text(text = "No Tasks Available")
            } else {
                LazyColumn(
                    state = lazyState,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(tasksList.value) { task->
                        TaskItem(
                            title = task.title,
                            description = task.description,
                            time = task.time
                        ) {
                            navController.navigate(
                                EditTaskScreenRoute(
                                    it.title,
                                    it.description,
                                    it.time
                                )
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun TaskItem(title:String, description:String, time:Long, onItemClick:(TaskModel)->Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color("#CFD8DC".toColorInt()), RoundedCornerShape(20.dp))
            .padding(12.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick(
                        TaskModel(
                            title = title,
                            description = description,
                            time = time
                        )
                    )
                }
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            Text(
                text = description,
                fontSize = 16.sp
            )
        }
    }
}
