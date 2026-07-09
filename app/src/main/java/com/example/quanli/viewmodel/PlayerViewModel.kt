package com.example.quanli.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quanli.database.DatabaseHelper
import com.example.quanli.model.Player

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)
    private val _playerList = MutableLiveData<List<Player>>()
    val playerList: LiveData<List<Player>> = _playerList

    fun loadPlayers() {
        _playerList.value = dbHelper.getAllPlayers()
    }

    // logic for adding, updating, deleting players would go here
}
