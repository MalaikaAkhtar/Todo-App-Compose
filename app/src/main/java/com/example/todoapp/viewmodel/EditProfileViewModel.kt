package com.example.todoapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class EditProfileViewModel : ViewModel()  {

    private val _name = mutableStateOf("Malaika")
    val name: State<String> = _name

    private val _nickname = mutableStateOf("nickname")
    val nickname: State<String> = _nickname

    fun updateName(newName: String) {
        _name.value = newName
    }

    fun updateNickname(newNickname: String) {
        _nickname.value = newNickname
    }
}