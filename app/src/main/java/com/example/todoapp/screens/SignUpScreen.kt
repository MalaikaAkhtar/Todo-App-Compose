package com.example.todoapp.screens

import android.widget.Toast
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.todoapp.R
import com.example.todoapp.activity.SpacerComponent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavHostController){

    val auth: FirebaseAuth = Firebase.auth

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingFromBaseline(70.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(text = stringResource(R.string.sign_up), fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
            //visualTransformation = PasswordVisualTransformation()
        )

        SpacerComponent(height = 16.dp)

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it.trim() },
            label = {
                Text(text = stringResource(R.string.confpassword), color = Color.Black)
            },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            )
            //visualTransformation = PasswordVisualTransformation(),
        )

        SpacerComponent(height = 48.dp)

        Button(onClick = {
            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Sign Up successful",
                                    Toast.LENGTH_SHORT).show()
                                navController.navigate("homeScreen")
                            } else {
                                Toast.makeText(context,
                                    "Sign Up failed: ${task.exception?.message}",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(context,"Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
                         },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF73FA)))
        {
            Text(text = stringResource(id = R.string.sign_up))
        }

        SpacerComponent(height = 16.dp)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = stringResource(id = R.string.have_account))
            TextButton(onClick = { navController.navigate("signInScreen") }) {
                Text(text = stringResource(id = R.string.sign_in), color = Color.Black)
            }
        }
    }
}



