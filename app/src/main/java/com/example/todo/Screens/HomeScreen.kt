package com.example.todo.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todo.DestinationScreen

@Composable
fun HomeScreen(navController: NavController) {
    HomeScreenContents(navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContents(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Tasks",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(DestinationScreen.EditTaskScreenRoute.route)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it.calculateTopPadding())
        )

    }
}
