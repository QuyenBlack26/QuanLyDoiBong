package com.example.quanli

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quanli.adapter.NewsAdapter
import com.example.quanli.model.News
import com.example.quanli.viewmodel.NewsViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val viewModel: NewsViewModel by viewModels()
    private lateinit var adapter: NewsAdapter

    private val addNewsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val title = data?.getStringExtra("title") ?: ""
            val content = data?.getStringExtra("content") ?: ""
            val imageUrl = data?.getStringExtra("imageUrl") ?: ""
            val date = data?.getStringExtra("date") ?: ""
            
            val newNews = News(
                id = (viewModel.newsList.value?.size ?: 0) + 1,
                title = title,
                content = content,
                imageUrl = imageUrl,
                date = date
            )
            viewModel.addNews(newNews)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        
        setupEdgeToEdge()
        setupRecyclerView()
        observeViewModel()
        setupFab()
    }

    private fun setupEdgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.newsRecyclerView)
        adapter = NewsAdapter(emptyList(), { news ->
            val intent = Intent(this, NewsDetailActivity::class.java).apply {
                putExtra("title", news.title)
                putExtra("content", news.content)
                putExtra("date", news.date)
                putExtra("imageUrl", news.imageUrl)
            }
            startActivity(intent)
        }, { news ->
            showDeleteDialog(news)
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun showDeleteDialog(news: News) {
        AlertDialog.Builder(this)
            .setTitle("Xóa tin tức")
            .setMessage("Bạn có chắc chắn muốn xóa tin tức này không?")
            .setPositiveButton("Xóa") { _, _ ->
                viewModel.deleteNews(news)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun observeViewModel() {
        viewModel.newsList.observe(this) { news ->
            adapter.updateData(news)
        }
    }

    private fun setupFab() {
        findViewById<FloatingActionButton>(R.id.addNewsFab).setOnClickListener {
            val intent = Intent(this, AddNewsActivity::class.java)
            addNewsLauncher.launch(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView
        
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterNews(newText ?: "")
                return true
            }
        })
        return true
    }
}
