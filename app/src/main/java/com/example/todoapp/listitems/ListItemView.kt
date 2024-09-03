package com.example.todoapp.listitems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.R
import com.example.todoapp.room.TodoList
import com.example.todoapp.dialog.BottomSheetDialog

@Composable
fun ListItemView(
    item: TodoList,
    navController: NavController,
    onChecked : (TodoList) -> Unit,
    onDelete: (TodoList) -> Unit,
    onFavorite: (TodoList) -> Unit ){

    var checkState by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(
                        id = if (item.isFavorite) R.drawable.ic_star
                        else R.drawable.ic_star_outline
                    ),
                    contentDescription = "Star Icon",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onFavorite(item) },
                    tint = if (item.isFavorite) Color(0xFFFDDA0D) else Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = item.title, fontWeight = FontWeight.Bold)
            }
            Text(text = item.description, Modifier.padding(start = 27.dp))
        }

        Icon(painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = "delete icon", modifier = Modifier.clickable{
            onDelete(item)
        })

        Checkbox(

            checked = checkState,
            onCheckedChange = {
                checkState = it
                showDialog = it
               },
            modifier = Modifier.clickable{ onChecked(item)}
        )
    }

    if (showDialog) {
    BottomSheetDialog(
        onDismiss = { showDialog = false },
        onComplete = {
            onChecked(item)
            showDialog = false
        }
    )
}
}

