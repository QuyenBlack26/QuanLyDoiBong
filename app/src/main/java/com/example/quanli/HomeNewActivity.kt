package com.example.quanli

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.quanli.activity.PlayerManagementActivity
import com.example.quanli.database.DatabaseHelper
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeNewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_new)

        // 1. Xử lý hiển thị thông tin người dùng
        UserHeaderUtils.setupUserHeader(this)

        val username = intent.getStringExtra("USERNAME")
        if (!username.isNullOrEmpty()) {
            Toast.makeText(this, "Chào mừng $username", Toast.LENGTH_SHORT).show()
        }

        // 2. Xử lý nút Đăng nhập ở góc trái trên cùng
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarNew)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // 2. Xử lý các Tab vụ ở dưới cùng
        NavigationUtils.setupBottomNavigation(this, R.id.nav_home)
    }
}