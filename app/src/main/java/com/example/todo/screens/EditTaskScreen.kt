package com.example.todo.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.todo.EditTaskScreenRoute
import com.example.todo.model.TaskModel
import com.example.todo.viewModel.MainViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.*
import java.time.format.DateTimeFormatter

@Composable
fun EditTaskScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    args: EditTaskScreenRoute
) {
    EditTaskScreenContents(navController, mainViewModel, args)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreenContents(navController: NavController, mainViewModel: MainViewModel, args: EditTaskScreenRoute) {
    var title by remember {
        mutableStateOf(args.title)
    }
    var description by remember {
        mutableStateOf(args.description)
    }
    var time by remember {
        mutableLongStateOf(args.time)
    }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Task",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .clickable {
                                if (validateFields(title, description)) {
                                    val task = TaskModel(
                                        title = title,
                                        description = description,
                                        time = time
                                    )
                                    mainViewModel.addTask(task)
                                    navController.popBackStack()
                                } else {
                                    Toast
                                        .makeText(
                                            context,
                                            "Please fill all the fields",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            }
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                TextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    textStyle = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        background = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = "Add a Title",
                            color = Color("#4A4A4A".toColorInt()),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 22.sp
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults
                        .colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                )
                Spacer(modifier = Modifier.height(16.dp))
                // TimePicker
                TimePickerDialogBox(
                    initialTime = time,
                    onTimeSelected = { selectedTime ->
                        time = selectedTime
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        background = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = "Add a Description",
                            color = Color("#4A4A4A".toColorInt()),
                            fontSize = 20.sp
                        )
                    },
                    colors = TextFieldDefaults
                        .colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                )
            }
        }
    }
}

fun validateFields(title: String, description: String): Boolean {
    return title.isNotEmpty() && description.isNotEmpty()
}

@Composable
fun TimePickerDialogBox(
    initialTime: Long,
    onTimeSelected: (Long) -> Unit
) {
    var pickedTime by remember {
        mutableStateOf(
            if (initialTime > 0) {
                Instant.ofEpochMilli(initialTime).atZone(ZoneId.systemDefault()).toLocalTime()
            } else {
                LocalTime.now()
            }
        )
    }

    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm a")
                .format(pickedTime)
        }
    }

    val dialogState = rememberMaterialDialogState()

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = formattedTime,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = Color(0xFF4A4A4A)
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .clickable {
                            dialogState.show()
                        }
                )
            }
        )
        MaterialDialog(
            dialogState = dialogState,
            buttons = {
                positiveButton(text = "Ok") {
                    val selectedTimeInMillis = pickedTime.atDate(LocalDate.now())
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli()
                    onTimeSelected(selectedTimeInMillis)
                }
                negativeButton(text = "Cancel")
            }
        ) {
            this.timepicker(
                initialTime = pickedTime
            ) {
                pickedTime = it
            }
        }
    }
}
