package com.example.quanli.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quanli.database.DatabaseHelper
import com.example.quanli.model.Match

class MatchViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)
    private val _matchList = MutableLiveData<List<Match>>()
    val matchList: LiveData<List<Match>> = _matchList

    fun loadMatches() {
        _matchList.value = dbHelper.getAllMatches()
    }
}
