package com.example.todoapp.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todoapp.R
import com.example.todoapp.activity.SpacerComponent
import com.example.todoapp.room.TodoList
import com.example.todoapp.sealed.TodoIntent
import com.example.todoapp.viewmodel.TodoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodos(viewModel: TodoViewModel, navController: NavController){

    var title by remember {
        mutableStateOf(" ")
    }

    var description by remember {
        mutableStateOf(" ")
    }
    val context = LocalContext.current
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingFromBaseline(40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Icon(painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Back arrow",
                modifier = Modifier.clickable { navController.popBackStack() })
            Text(text = stringResource(R.string.add_todos),fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 5.dp ,end = 150.dp))
        }

        SpacerComponent(height = 16.dp)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it }, label = {
                Text(text = stringResource(R.string.enter_title), color = Color.Black) },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ))

        SpacerComponent(height = 16.dp)

        OutlinedTextField(
            value = description,
            onValueChange = { description = it }, label = {
                Text(text = stringResource(R.string.enter_description), color = Color.Black) },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ))

        SpacerComponent(height = 32.dp)

        Button(
            onClick = {
                if (title.isNotBlank() && description.isNotBlank()) {
                    val todo = TodoList(title = title, description = description)
                    viewModel.handleIntent(TodoIntent.AddTodo(todo))
                    Toast.makeText(context, "Todo added", Toast.LENGTH_SHORT).show()
                    navController.navigateUp()
                } else {
                    Toast.makeText(context, "Please enter title and description",
                        Toast.LENGTH_SHORT).show()
                }
                      },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF73FA))
        ){
            Text(text = stringResource(R.string.add))
        }
    }
}