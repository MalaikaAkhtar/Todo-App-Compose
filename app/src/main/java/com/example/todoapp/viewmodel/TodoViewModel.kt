package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.repository.TodoRepository
import com.example.todoapp.room.TodoDatabase
import com.example.todoapp.room.TodoList
import com.example.todoapp.room.TodoState
import com.example.todoapp.sealed.TodoIntent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TodoRepository

    private val _state = MutableStateFlow(TodoState())
    val state: StateFlow<TodoState> = _state.asStateFlow()

    private val _showFavorites = MutableStateFlow(false)
    val showFavorites: StateFlow<Boolean> get() = _showFavorites.asStateFlow()

    private val _showCompleted = MutableStateFlow(false)
    val showCompleted: StateFlow<Boolean> get() = _showCompleted.asStateFlow()


    init {
        val todoDao = TodoDatabase.getInstance(application).todoDao()
        repository = TodoRepository(todoDao)
        handleIntent(TodoIntent.LoadTodos)
    }

//    fun toggleShowFavorites() {
//        _showFavorites.value = !_showFavorites.value
//        handleIntent(if (_showFavorites.value) TodoIntent.LoadFavorites else TodoIntent.LoadTodos)
//    }
//    fun toggleShowCompleted() {
//        _showCompleted.value = !_showCompleted.value
//        handleIntent(if (_showCompleted.value)TodoIntent.LoadCompleted else TodoIntent.LoadTodos)
//    }

    fun setShowFavorites(value: Boolean) {
        _showFavorites.value = value
        loadFilteredTodos()
    }

    fun setShowCompleted(value: Boolean) {
        _showCompleted.value = value
        loadFilteredTodos()
    }

    fun handleIntent(intent: TodoIntent) {
        when (intent) {
            is TodoIntent.AddTodo -> addTodo(intent.todo)
            is TodoIntent.UpdateTodo -> updateTodo(intent.todo)
            is TodoIntent.DeleteTodo -> deleteTodo(intent.todo)
            TodoIntent.LoadTodos -> loadTodos()
            TodoIntent.LoadFavorites -> loadFavorites()
            TodoIntent.LoadCompleted -> loadCompleted()
        }
    }

    private fun loadFilteredTodos() {
        when {
            _showFavorites.value -> handleIntent(TodoIntent.LoadFavorites)
            _showCompleted.value -> handleIntent(TodoIntent.LoadCompleted)
            else -> handleIntent(TodoIntent.LoadTodos)
        }
    }

    private fun addTodo(todo: TodoList) = viewModelScope.launch {
        repository.insert(todo)
        loadTodos()
    }

    private fun updateTodo(todo: TodoList) = viewModelScope.launch {
        repository.update(todo)
        loadTodos()
    }

    private fun deleteTodo(todo: TodoList) = viewModelScope.launch {
        repository.delete(todo)
        loadTodos()
    }

    private fun loadTodos() = viewModelScope.launch {
        repository.getAllTodos()
            .onStart { _state.value = _state.value.copy(isLoading = true) }
            .collect { todos ->
                _state.value = _state.value.copy(todos = todos, isLoading = false)
            }
    }

    private fun loadFavorites() = viewModelScope.launch {
        repository.getFavoriteTodos()
            .onStart { _state.value = _state.value.copy(isLoading = true) }
            .collect { todos ->
                _state.value = _state.value.copy(todos = todos, isLoading = false)
            }
    }

    private fun loadCompleted() = viewModelScope.launch {
        repository.getCompletedTodos()
            .onStart { _state.value = _state.value.copy(isLoading = true) }
            .collect { todos ->
                _state.value = _state.value.copy(todos = todos, isLoading = false)
            }
    }

}
