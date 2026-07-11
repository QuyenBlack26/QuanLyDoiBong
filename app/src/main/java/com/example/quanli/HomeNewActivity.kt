package com.example.quanli

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quanli.activity.PlayerManagementActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeNewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_new)

        // 1. Xử lý nút Đăng nhập ở góc trái trên cùng
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