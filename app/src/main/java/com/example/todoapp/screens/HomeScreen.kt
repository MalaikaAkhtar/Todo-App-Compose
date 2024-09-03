package com.example.todoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todoapp.listitems.ListItemView
import com.example.todoapp.R
import com.example.todoapp.activity.SpacerComponent
import com.example.todoapp.sealed.TodoIntent
import com.example.todoapp.viewmodel.EditProfileViewModel
import com.example.todoapp.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: TodoViewModel,
               editViewModel: EditProfileViewModel,
               navController: NavController) {
    val state by viewModel.state.collectAsState()
    val showFavorites by viewModel.showFavorites.collectAsState()
    val showCompleted by viewModel.showCompleted.collectAsState()

    val name by editViewModel.name
    val nickname by editViewModel.nickname

    LaunchedEffect(Unit) {
        viewModel.handleIntent(TodoIntent.LoadTodos)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingFromBaseline(40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.home), fontSize = 20.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 120.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = stringResource(R.string.settings),
                    modifier = Modifier
                        .padding(start = 140.dp, end = 10.dp)
                        .clickable { navController.navigate("settings") }
                )
            }
        }

        SpacerComponent(height = 20.dp)

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(modifier = Modifier.padding(start = 10.dp)) {
                    Text(text = stringResource(R.string.welcome_back), fontSize = 20.sp)
                    //Text(text = name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = "$name ($nickname)", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(45.dp)
                        .clip(CircleShape)
                        .clickable { navController.navigate("editProfile") }
                )
            }
        }

        SpacerComponent(height = 16.dp)

        // Search Bar
        var searchText by remember {
            mutableStateOf("")
        }

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text(stringResource(R.string.search), color = Color.Black)},
            singleLine = true,
            maxLines = 1,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(R.string.search_icon)
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
        )

        SpacerComponent(height = 10.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {  viewModel.setShowFavorites(false)
                                 viewModel.setShowCompleted(false)},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if ((!showFavorites) && (!showCompleted))
                            Color(0xFFFF73FA)
                        else Color.LightGray
                    ),
                ) {
                    Text(text = stringResource(R.string.all))
                }

                SpacerComponent(height = 5.dp)

                Button(
                    onClick = {viewModel.setShowCompleted(true)
                               viewModel.setShowFavorites(false)
                              },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if ((showCompleted)&& (!showFavorites))
                            Color(0xFFFF73FA)
                    else Color.LightGray)

                ) {
                    Text(text = stringResource(R.string.completed))
                }

                SpacerComponent(height = 5.dp)

                Button(
                    onClick = { viewModel.setShowFavorites(true)
                                viewModel.setShowCompleted(false)
                              },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if ((showFavorites)&& (!showCompleted))
                            Color(0xFFFF73FA)
                        else Color.LightGray
                    ),
                ) {
                    Text(text = stringResource(R.string.favorite))
                }
            }

            // for list item
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 26.dp)
            ) {
                val filteredTodos = state.todos
                    .filter { todo ->
                        val matchesFavoriteFilter = if (showFavorites) todo.isFavorite else true
                        val matchesCompletedFilter = if (showCompleted) todo.isCompleted else true
                        val matchesSearchText = todo.title.contains(searchText, ignoreCase = true)||
                                todo.description.contains(searchText, ignoreCase = true)
                        matchesFavoriteFilter && matchesCompletedFilter && matchesSearchText
                    }
                items(filteredTodos) { item ->
                    ListItemView(
                        item,
                        navController = navController,
                        onDelete = { viewModel.handleIntent(TodoIntent.DeleteTodo(it)) },
                        onFavorite = { todos ->
                            val updatedTodo = todos.copy(isFavorite = !todos.isFavorite)
                            viewModel.handleIntent(TodoIntent.UpdateTodo(updatedTodo))
                        },
                        onChecked = {
                                todos ->
                            val updatedTodo = todos.copy(isCompleted = true)
                            viewModel.handleIntent(TodoIntent.UpdateTodo(updatedTodo))
                        }
                    )
                    Divider()
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        FloatingActionButton(
            onClick = { navController.navigate("add-todos") },
            modifier = Modifier.padding(5.dp),
            containerColor = Color(0xFFFF73FA)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add Task"
            )
        }
    }
}


