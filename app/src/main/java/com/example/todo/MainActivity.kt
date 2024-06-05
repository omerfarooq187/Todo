package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.todo.screens.EditTaskScreen
import com.example.todo.screens.HomeScreen
import com.example.todo.ui.theme.ToDoTheme
import com.example.todo.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

    @Serializable
    data object HomeScreenRoute
    @Serializable
    data class EditTaskScreenRoute  (
        val title:String,
        val description:String,
        val time: Long
    )

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val mainViewModel = ViewModelProvider(this)[MainViewModel::class]
        setContent {
            ToDoTheme {
                App(mainViewModel)
            }
        }
    }
}

@Composable
fun App(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeScreenRoute
    ) {
        composable<HomeScreenRoute> {
            HomeScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable<EditTaskScreenRoute> {
            val args = it.toRoute<EditTaskScreenRoute>()
            EditTaskScreen(navController, mainViewModel, args)
        }
    }
}

