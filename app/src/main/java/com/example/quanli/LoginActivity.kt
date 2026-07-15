package com.example.quanli

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.quanli.viewmodel.AuthViewModel
import com.example.quanli.database.DatabaseHelper
import com.example.quanli.model.UserAccount

class LoginActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dangnhap)

        val edtUser = findViewById<EditText>(R.id.edtUser)
        val edtPass = findViewById<EditText>(R.id.edtPass)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val txtRegister = findViewById<TextView>(R.id.txtRegister)

        btnLogin?.setOnClickListener {
            val user = edtUser.text.toString()
            val pass = edtPass.text.toString()

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.login(user, pass)
            }
        }

        viewModel.loginStatus.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                
                // Save session
                val username = edtUser.text.toString().trim()
                val dbHelper = DatabaseHelper(this)
                val user = dbHelper.getUserByUsername(username)
                if (user != null) {
                    SessionManager(this).saveUser(user)
                }

                val intent = Intent(this, HomeNewActivity::class.java)
                intent.putExtra("USERNAME", username)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show()
            }
        }

        txtRegister?.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}