package com.example.todoapp.screens

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todoapp.R
import com.example.todoapp.activity.SpacerComponent
import java.util.Locale

@Composable
fun Settings(navController: NavController, isDarkTheme: Boolean, onThemeChange: () -> Unit) {

    var expanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("English") }

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
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Back arrow",
                modifier = Modifier.clickable { navController.popBackStack() }
            )
            Text(
                    text = stringResource(R.string.settings),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 5.dp, end = 150.dp)
            )
        }
        SpacerComponent(height = 32.dp)

        Text(
            text = stringResource(R.string.general),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 15.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dark_mode),
                        contentDescription = "Dark theme"
                    )
                    Text(text = stringResource(R.string.dark_theme), fontSize = 18.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        modifier = Modifier.semantics { contentDescription = "Switch button" },
                        checked = isDarkTheme,
                        onCheckedChange = { onThemeChange() },
                        thumbContent = {
                            if (isDarkTheme) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(10.dp)
                                )
                            }
                        },
                        colors = SwitchDefaults.colors(
                            uncheckedThumbColor = Color.Gray,
                            checkedThumbColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
                SpacerComponent(height = 10.dp)
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_language),
                        contentDescription = "Language"
                    )
                    Text(text = stringResource(R.string.language), fontSize = 18.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Box {
                        Text(
                            text = selectedLanguage,
                            modifier = Modifier
                                .clickable { expanded = true }
                                .padding(8.dp)
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    selectedLanguage = "English"
                                    expanded = false
                                    setLocale(navController.context, "en")
                                },
                                text = { Text(stringResource(R.string.english)) }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    selectedLanguage = "Urdu"
                                    expanded = false
                                    setLocale(navController.context, "ur")
                                },
                                text = { Text(stringResource(R.string.urdu)) }
                            )
                        }
                    }
                }
            }
        }
        SpacerComponent(height = 16.dp)

        Text(
            text = stringResource(R.string.timer),
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 5.dp),
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_timereset),
                        contentDescription = "Time Reset"
                    )
                    Text(text = stringResource(R.string.time_reset), fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(25.dp))
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_timerwork),
                        contentDescription = "Timer work"
                    )
                    Text(text = stringResource(R.string.timer_work), fontSize = 18.sp)
                }
            }
        }

        Button(onClick = {
 //           googleAuth.signOut()
            navController.navigate("signInScreen")

        },modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF73FA))
        ) {
            Text(text = stringResource(id = R.string.sign_out))
            
        }
    }
}

fun setLocale(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = Configuration()
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}
