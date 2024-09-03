package com.example.todoapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todoList: TodoList)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(todoList: TodoList)

    @Delete
    suspend fun delete(todoList: TodoList)

    @Query("SELECT * FROM todo_list")
    fun getAllTodos(): Flow<List<TodoList>>

    @Query("SELECT * FROM todo_list WHERE favorite = 1")
    fun getFavoriteTodos(): Flow<List<TodoList>>

    @Query("SELECT * FROM todo_list WHERE completed = 1")
    fun getCompletedTodos(): Flow<List<TodoList>>

}


