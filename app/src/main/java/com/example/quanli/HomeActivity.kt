package com.example.quanli

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_member_detail -> {
                    // Chuyển đến trang chi tiết thành viên
                    true
                }
                R.id.nav_match_history -> {
                    // Chuyển đến trang lịch sử thi đấu
                    true
                }
                R.id.nav_news -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_member_management -> {
                    // Chuyển đến trang quản lý thành viên
                    true
                }
                R.id.nav_account -> {
                    // Chuyển đến trang đăng nhập / đăng ký
                    true
                }
                else -> false
            }
        }
    }
}
