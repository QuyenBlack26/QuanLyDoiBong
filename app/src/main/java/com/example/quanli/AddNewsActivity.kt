package com.example.quanli

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_news)

        val editTitle = findViewById<TextInputEditText>(R.id.editTitle)
        val editContent = findViewById<TextInputEditText>(R.id.editContent)
        val editImageUrl = findViewById<TextInputEditText>(R.id.editImageUrl)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val title = editTitle.text.toString()
            val content = editContent.text.toString()
            val imageUrl = editImageUrl.text.toString()
            val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

            if (title.isNotEmpty() && content.isNotEmpty()) {
                val resultIntent = Intent().apply {
                    putExtra("title", title)
                    putExtra("content", content)
                    putExtra("imageUrl", if (imageUrl.isEmpty()) "https://via.placeholder.com/600/default" else imageUrl)
                    putExtra("date", date)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
        
        supportActionBar?.title = "Thêm tin tức"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
