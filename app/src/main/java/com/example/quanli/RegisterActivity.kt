package com.example.quanli

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.quanli.viewmodel.AuthViewModel

class RegisterActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dangky)

        val edtHoTen = findViewById<EditText>(R.id.edtHoTen)
        val edtUser = findViewById<EditText>(R.id.edtUser)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPass = findViewById<EditText>(R.id.edtPass)
        val edtRePass = findViewById<EditText>(R.id.edtRePass)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val txtLogin = findViewById<TextView>(R.id.txtLogin)

        btnRegister?.setOnClickListener {
            val hoTen = edtHoTen.text.toString()
            val user = edtUser.text.toString()
            val email = edtEmail.text.toString()
            val pass = edtPass.text.toString()
            val rePass = edtRePass.text.toString()

            if (hoTen.isEmpty() || user.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != rePass) {
                Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = viewModel.register(user, pass, email, hoTen)
            if (result > -1) {
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Tên đăng nhập đã tồn tại hoặc lỗi đăng ký", Toast.LENGTH_SHORT).show()
            }
        }

        txtLogin?.setOnClickListener {
            finish()
        }
    }
}