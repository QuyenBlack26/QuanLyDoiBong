package com.example.quanli

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        val title = intent.getStringExtra("title") ?: ""
        val content = intent.getStringExtra("content") ?: ""
        val date = intent.getStringExtra("date") ?: ""
        val imageUrl = intent.getStringExtra("imageUrl") ?: ""

        findViewById<TextView>(R.id.detailTitle).text = title
        findViewById<TextView>(R.id.detailContent).text = content
        findViewById<TextView>(R.id.detailDate).text = date
        
        val imageView = findViewById<ImageView>(R.id.detailImage)
        Glide.with(this)
            .load(imageUrl)
            .placeholder(android.R.color.darker_gray)
            .into(imageView)
            
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Chi tiết tin tức"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
