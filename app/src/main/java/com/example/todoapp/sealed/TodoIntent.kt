package com.example.todoapp.sealed

import com.example.todoapp.room.TodoList

sealed class TodoIntent {
    data class AddTodo(val todo: TodoList) : TodoIntent()
    data class UpdateTodo(val todo: TodoList) : TodoIntent()
    data class DeleteTodo(val todo: TodoList) : TodoIntent()
    object LoadTodos : TodoIntent()
    object LoadFavorites : TodoIntent()
    object LoadCompleted : TodoIntent()
}