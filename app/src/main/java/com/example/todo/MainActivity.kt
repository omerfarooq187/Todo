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
import com.example.todo.screens.EditTaskScreen
import com.example.todo.screens.HomeScreen
import com.example.todo.ui.theme.ToDoTheme
import com.example.todo.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

sealed class DestinationScreen(val route: String) {
    data object HomeScreenRoute : DestinationScreen("home")
    data object EditTaskScreenRoute : DestinationScreen("edit")
}

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
        startDestination = DestinationScreen.HomeScreenRoute.route
    ) {
        composable(DestinationScreen.HomeScreenRoute.route) {
            HomeScreen(navController, mainViewModel)
        }
        composable(DestinationScreen.EditTaskScreenRoute.route) {
            EditTaskScreen(navController, mainViewModel)
        }
    }
}

