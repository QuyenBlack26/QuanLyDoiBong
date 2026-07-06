package com.example.quanli

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quanli.adapter.PlayerAdapter
import com.example.quanli.database.DatabaseHelper
import com.example.quanli.model.Player
import com.google.android.material.appbar.MaterialToolbar

class HomeActivity : AppCompatActivity() {

    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        dbHelper = DatabaseHelper(this)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setOnMenuItemClickListener { menuItem ->
            handleNavigation(menuItem)
        }

        setupRecyclerView()
        loadPlayersFromDatabase()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.playerRecyclerView)
        playerAdapter = PlayerAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = playerAdapter
    }

    private fun loadPlayersFromDatabase() {
        val players = dbHelper.getAllPlayers()
        playerAdapter.updateData(players)
    }

    private fun handleNavigation(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_member_detail -> {
                // Chuyển đến trang chi tiết thành viên
                return true
            }
            R.id.nav_match_history -> {
                // Chuyển đến trang lịch sử thi đấu
                return true
            }
            R.id.nav_news -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_member_management -> {
                // Chuyển đến trang quản lý thành viên
                return true
            }
            R.id.nav_account -> {
                // Chuyển đến trang đăng nhập / đăng ký
                return true
            }
        }
        return false
    }
}
