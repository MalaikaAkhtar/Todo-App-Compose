package com.example.todoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todoapp.R
import com.example.todoapp.activity.SpacerComponent
import com.example.todoapp.viewmodel.EditProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(editViewModel: EditProfileViewModel, navController: NavController){

    var name by remember {
        mutableStateOf("")
    }

    var nickname by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .paddingFromBaseline(40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Icon(painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Back arrow",
                modifier = Modifier.clickable { navController.popBackStack() })
            Text(text = stringResource(R.string.edit_profile),fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 5.dp ,end = 140.dp))
        }

        SpacerComponent(height = 32.dp)

        Image(painter = painterResource(id = R.drawable.profile),
            contentDescription = "profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 20.dp)
                .size(150.dp)
                .clip(CircleShape))

        SpacerComponent(height = 32.dp)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it }, label = {
                Text(text = stringResource(R.string.enter_name), color = Color.Black) },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ))

        SpacerComponent(height = 16.dp)

        OutlinedTextField(
            value = nickname,
            onValueChange = { nickname = it }, label = {
                Text(text = stringResource(R.string.enter_nickname), color = Color.Black) },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ))

        SpacerComponent(height = 32.dp)

        Button(onClick = {
            editViewModel.updateName(name)
            editViewModel.updateNickname(nickname)
            navController.popBackStack() },
            modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF73FA)))
        {
            Text(text = stringResource(R.string.save))
        }
    }
}