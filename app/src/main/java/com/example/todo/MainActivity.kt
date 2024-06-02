package com.example.todo

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.Screens.EditTaskScreen
import com.example.todo.Screens.HomeScreen
import com.example.todo.ui.theme.ToDoTheme
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
        setContent {
            ToDoTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = DestinationScreen.HomeScreenRoute.route
    ) {
        composable(DestinationScreen.HomeScreenRoute.route) {
            HomeScreen(navController)
        }
        composable(DestinationScreen.EditTaskScreenRoute.route) {
            EditTaskScreen(navController)
        }
    }
}

