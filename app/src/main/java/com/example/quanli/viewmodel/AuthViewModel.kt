package com.example.quanli.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quanli.database.DatabaseHelper
import com.example.quanli.model.UserAccount

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)
    
    private val _currentUser = MutableLiveData<UserAccount?>()
    val currentUser: LiveData<UserAccount?> = _currentUser

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> = _loginStatus

    fun login(username: String, password: String) {
        val success = dbHelper.checkLogin(username, password)
        if (success) {
            _currentUser.value = dbHelper.getUserByUsername(username)
        }
        _loginStatus.value = success
    }

    fun register(username: String, password: String, email: String, fullName: String): Long {
        return dbHelper.registerUser(username, password, email, fullName)
    }

    fun logout() {
        _currentUser.value = null
        _loginStatus.value = false
    }
}
