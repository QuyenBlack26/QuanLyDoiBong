package com.example.quanli

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.example.quanli.activity.PlayerManagementActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

object NavigationUtils {

    fun setupBottomNavigation(activity: Activity, currentItemId: Int) {
        val bottomNav = activity.findViewById<BottomNavigationView>(R.id.bottomNavigation) ?: return
        
        // Set the current item as selected
        bottomNav.selectedItemId = currentItemId
        
        bottomNav.setOnItemSelectedListener { item ->
            if (item.itemId == currentItemId) return@setOnItemSelectedListener true
            
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(activity, HomeNewActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    activity.startActivity(intent)
                    if (activity !is HomeNewActivity) activity.finish()
                    true
                }
                R.id.nav_detail -> {
                    val intent = Intent(activity, HomeActivity::class.java)
                    intent.putExtra("TAB_INDEX", 3) // Tab ĐỘI HÌNH (Chi tiết)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    activity.startActivity(intent)
                    if (activity !is HomeActivity) activity.finish()
                    true
                }
                R.id.nav_history -> {
                    val intent = Intent(activity, HomeActivity::class.java)
                    intent.putExtra("TAB_INDEX", 0) // Tab KẾT QUẢ (Lịch sử)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    activity.startActivity(intent)
                    if (activity !is HomeActivity) activity.finish()
                    true
                }
                R.id.nav_news -> {
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    activity.startActivity(intent)
                    if (activity !is MainActivity) activity.finish()
                    true
                }
                R.id.nav_admin -> {
                    val sessionManager = SessionManager(activity)
                    if (sessionManager.getRole().equals("admin", ignoreCase = true)) {
                        val intent = Intent(activity, PlayerManagementActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                        activity.startActivity(intent)
                        if (activity !is PlayerManagementActivity) activity.finish()
                        true
                    } else {
                        Toast.makeText(activity, "Quyền truy cập không đủ. Chỉ dành cho Admin.", Toast.LENGTH_LONG).show()
                        false
                    }
                }
                else -> false
            }
        }
    }
}
