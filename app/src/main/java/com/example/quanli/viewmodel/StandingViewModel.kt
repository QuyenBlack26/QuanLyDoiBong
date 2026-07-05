package com.example.quanli.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quanli.database.DatabaseHelper
import com.example.quanli.model.Standing

class StandingViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)
    private val _standingList = MutableLiveData<List<Standing>>()
    val standingList: LiveData<List<Standing>> = _standingList

    fun loadStandings() {
        _standingList.value = dbHelper.getAllStandings()
    }
}
