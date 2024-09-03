package com.example.todoapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.screens.AddTodos
import com.example.todoapp.screens.EditProfile
import com.example.todoapp.screens.HomeScreen
import com.example.todoapp.screens.Settings
import com.example.todoapp.screens.SignInScreen
import com.example.todoapp.screens.SignUpScreen
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.viewmodel.EditProfileViewModel
import com.example.todoapp.viewmodel.TodoViewModel


const val WEB_CLIENT_ID = "297603121396-7vruooqm9mg5tl406hgbs0m8033n1gn1.apps.googleusercontent.com"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var isDarkTheme by remember { mutableStateOf(false) }

            TodoAppTheme(darkTheme = isDarkTheme) {

                val viewModel: TodoViewModel = viewModel()
                val editViewModel: EditProfileViewModel = viewModel()
                val navController = rememberNavController()

                MainScreen(
                    navController = navController,
                    viewModel = viewModel,
                    editViewModel = editViewModel,
                    isDarkTheme = isDarkTheme,
                    onThemeChange = { isDarkTheme = !isDarkTheme },

                )
            }
        }
    }
}

@Composable
fun MainScreen(
    navController: androidx.navigation.NavHostController,
    viewModel: TodoViewModel,
    editViewModel: EditProfileViewModel,
    isDarkTheme: Boolean,
    onThemeChange: () -> Unit
) {
    NavHost(navController = navController, startDestination = "signInScreen") {

        composable("signInScreen") {
            SignInScreen(navController = navController)
        }
        composable("signUpScreen"){
            SignUpScreen(navController = navController)
        }
        composable("homeScreen") {
            HomeScreen(viewModel, editViewModel, navController = navController)
        }
        composable("settings") {
            Settings(navController = navController, isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange)
        }
        composable("add-todos") {
            AddTodos(viewModel, navController = navController)
        }
        composable("editProfile") {
            EditProfile(editViewModel, navController = navController)
        }
    }
}

@Composable
fun SpacerComponent(height: Dp = Dp.Unspecified, width: Dp = Dp.Unspecified) {
    Spacer(
        modifier = Modifier
            .height(height)
            .width(width)
    )
}










