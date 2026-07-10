package com.example.quanli
import com.example.quanli.fragment.TeamDetailFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
// Tí nữa dán xong mà chữ TeamDetailFragment bị đỏ, ông bấm Alt + Enter vào nó để tự Import nha!

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Chiêu "lấn sân": Bỏ qua giao diện activity_main của bạn ông.
        // Ép hệ thống nhét thẳng cái Fragment chi tiết đội bóng của ông lên full màn hình!
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, TeamDetailFragment())
            .commit()
    }
}