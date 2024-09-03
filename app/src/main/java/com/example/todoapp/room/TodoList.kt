package com.example.todoapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_list")
data class TodoList(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "favorite")
    var isFavorite: Boolean = false,
    @ColumnInfo(name = "completed")
    var isCompleted: Boolean = false

    )

data class TodoState(
    val todos: List<TodoList> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)