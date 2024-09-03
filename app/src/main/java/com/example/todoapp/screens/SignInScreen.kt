package com.example.todoapp.screens

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.todoapp.authentication.GoogleAuth
import com.example.todoapp.R
import com.example.todoapp.activity.SpacerComponent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavHostController) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val googleAuth = remember { GoogleAuth(context, scope = scope) }
    val auth: FirebaseAuth = Firebase.auth

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingFromBaseline(70.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(text = stringResource(R.string.sign_in), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        SpacerComponent(height = 110.dp)

        OutlinedTextField(
            value = email,
            onValueChange = { email = it.trim() },
            label = { Text(text = stringResource(R.string.email), color = Color.Black) },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            )
        )

        SpacerComponent(height = 16.dp)

        OutlinedTextField(
            value = password,
            onValueChange = { password = it.trim() },
            label = {
                Text(text = stringResource(R.string.password), color = Color.Black)
            },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            )
        )

        SpacerComponent(height = 48.dp)

        Button(
            onClick = {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                navController.navigate("homeScreen")
                                Toast.makeText(context, "Sign In successful",
                                    Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context,
                                    "Authentication failed: ${task.exception?.message}",
                                    Toast.LENGTH_LONG).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Invalid email format", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Enter email and password", Toast.LENGTH_SHORT).show()
            }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF73FA))
        ) {
            Text(text = stringResource(R.string.sign_in))
        }

        SpacerComponent(height = 8.dp)

        Text(text = stringResource(R.string.else1))

        SpacerComponent(height = 8.dp)

        Button(
            onClick = {

                googleAuth.signInWithGoogle(
                    onAuthComplete = {
                        navController.navigate("homeScreen")
                        Toast.makeText(context, "Google Sign-In successful",
                            Toast.LENGTH_SHORT).show()
                    },
                    onAuthError = { e ->
                        Toast.makeText(context, "Authentication failed: ${e.message}",
                            Toast.LENGTH_LONG).show()
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Image(painter = painterResource(id = R.drawable.googlelogo),
                contentDescription = "Google Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape))

            SpacerComponent(height = 12.dp)

            Text(text = stringResource(R.string.signIn_google))
        }

        SpacerComponent(height = 16.dp)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = stringResource(id = R.string.find_account))
            TextButton(onClick = { navController.navigate("signUpScreen") }) {
                Text(text = stringResource(id = R.string.sign_up), color = Color.Black)
            }
        }
    }
}





