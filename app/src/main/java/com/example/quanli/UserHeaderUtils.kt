package com.example.quanli

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

object UserHeaderUtils {

    fun setupUserHeader(activity: Activity) {
        val layoutUserInfo = activity.findViewById<LinearLayout>(R.id.layoutUserInfo) ?: return
        val tvUserName = activity.findViewById<TextView>(R.id.tvUserName) ?: return
        val tvUserRole = activity.findViewById<TextView>(R.id.tvUserRole) ?: return

        val sessionManager = SessionManager(activity)
        if (sessionManager.isLoggedIn()) {
            layoutUserInfo.visibility = View.VISIBLE
            tvUserName.text = sessionManager.getFullName() ?: sessionManager.getUsername()
            tvUserRole.text = "Vai trò: ${sessionManager.getRole()}"

            layoutUserInfo.setOnClickListener {
                showUserOptionsDialog(activity, sessionManager)
            }
        } else {
            layoutUserInfo.visibility = View.GONE
        }
    }

    private fun showUserOptionsDialog(activity: Activity, sessionManager: SessionManager) {
        val options = arrayOf("Đăng xuất", "Hủy")
        AlertDialog.Builder(activity)
            .setTitle("Tài khoản: ${sessionManager.getUsername()}")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> { // Logout
                        sessionManager.logout()
                        val intent = Intent(activity, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        activity.startActivity(intent)
                        activity.finish()
                    }
                    else -> dialog.dismiss()
                }
            }
            .show()
    }
}
