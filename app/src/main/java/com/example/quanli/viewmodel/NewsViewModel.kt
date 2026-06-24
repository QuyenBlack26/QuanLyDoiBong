package com.example.quanli.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quanli.model.News

class NewsViewModel : ViewModel() {
    private val allNews = mutableListOf<News>()
    private val _newsList = MutableLiveData<List<News>>()
    val newsList: LiveData<List<News>> = _newsList

    init {
        loadDummyNews()
    }

    private fun loadDummyNews() {
        allNews.addAll(listOf(
            News(1, "Kết quả trận đấu tối qua", "Đội tuyển đã giành chiến thắng thuyết phục 3-0 trước đối thủ kình địch...", "https://via.placeholder.com/600/92c952", "24/06/2026"),
            News(2, "Thông báo lịch tập luyện", "Lịch tập luyện tuần tới sẽ có sự thay đổi do điều kiện thời tiết...", "https://via.placeholder.com/600/771796", "23/06/2026"),
            News(3, "Bản hợp đồng mới", "Câu lạc bộ vừa chính thức ký kết hợp đồng với tiền đạo trẻ tài năng...", "https://via.placeholder.com/600/24f355", "22/06/2026")
        ))
        _newsList.value = allNews
    }

    fun addNews(news: News) {
        allNews.add(0, news)
        _newsList.value = allNews
    }

    fun deleteNews(news: News) {
        allNews.remove(news)
        _newsList.value = allNews
    }

    fun filterNews(query: String) {
        if (query.isEmpty()) {
            _newsList.value = allNews
        } else {
            _newsList.value = allNews.filter { 
                it.title.contains(query, ignoreCase = true) || it.content.contains(query, ignoreCase = true) 
            }
        }
    }
}
