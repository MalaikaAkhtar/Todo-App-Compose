package com.example.todoapp.repository

import com.example.todoapp.room.TodoDao
import com.example.todoapp.room.TodoList
import kotlinx.coroutines.flow.Flow

class TodoRepository (private val todoDao: TodoDao) {

    suspend fun insert(todoList: TodoList) {
        todoDao.insert(todoList)
    }

    suspend fun update(todoList: TodoList) {
        todoDao.update(todoList)
    }

    suspend fun delete(todoList: TodoList) {
        todoDao.delete(todoList)
    }

     fun getAllTodos(): Flow<List<TodoList>> {
        return todoDao.getAllTodos()
    }

    fun getFavoriteTodos(): Flow<List<TodoList>> {
        return todoDao.getFavoriteTodos()
    }

    fun getCompletedTodos(): Flow<List<TodoList>> {
        return todoDao.getCompletedTodos()
    }



}