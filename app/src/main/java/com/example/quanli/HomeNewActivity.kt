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
        val username = intent.getStringExtra("USERNAME")
        if (!username.isNullOrEmpty()) {
            Toast.makeText(this, "Chào mừng $username", Toast.LENGTH_SHORT).show()
            val dbHelper = DatabaseHelper(this)
            val user = dbHelper.getUserByUsername(username)
            if (user != null) {
                val layoutUserInfo = findViewById<LinearLayout>(R.id.layoutUserInfo)
                val tvUserName = findViewById<TextView>(R.id.tvUserName)
                val tvUserRole = findViewById<TextView>(R.id.tvUserRole)
                
                layoutUserInfo.visibility = View.VISIBLE
                tvUserName.text = user.fullName ?: user.username
                tvUserRole.text = "Vai trò: ${user.role}"
                
                // Ẩn nút "Menu" (hoặc icon navigation) nếu đã đăng nhập để thay bằng info
                // toolbar.navigationIcon = null
            }
        }

        // 2. Xử lý nút Đăng nhập ở góc trái trên cùng
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarNew)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // 2. Xử lý các Tab vụ ở dưới cùng
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Bạn đang ở Trang chủ", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_detail -> {
                    // Chuyển đến trang danh sách thành viên
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_history -> {
                    Toast.makeText(this, "Mở trang Lịch sử thi đấu", Toast.LENGTH_SHORT).show()
                    // startActivity(Intent(this, MatchHistoryActivity::class.java))
                    true
                }
                R.id.nav_news -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_admin -> {
                    val intent = Intent(this, PlayerManagementActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}