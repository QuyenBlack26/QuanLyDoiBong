package com.example.quanli
import com.example.quanli.fragment.TeamDetailFragment
import android.os.Bundle
<<<<<<< HEAD
import android.view.Menu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quanli.adapter.NewsAdapter
import com.example.quanli.model.News
import com.example.quanli.viewmodel.NewsViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
=======
import androidx.appcompat.app.AppCompatActivity
// Tí nữa dán xong mà chữ TeamDetailFragment bị đỏ, ông bấm Alt + Enter vào nó để tự Import nha!
>>>>>>> Trí

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< HEAD
        setContentView(R.layout.activity_main)
        
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        
        setupRecyclerView()
        observeViewModel()
        setupFab()
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
=======

        // Chiêu "lấn sân": Bỏ qua giao diện activity_main của bạn ông.
        // Ép hệ thống nhét thẳng cái Fragment chi tiết đội bóng của ông lên full màn hình!
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, TeamDetailFragment())
            .commit()
    }
}
>>>>>>> Trí
