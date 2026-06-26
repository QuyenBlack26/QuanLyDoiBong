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
            News(1, 
                "Việt Nam sẵn sàng cho vòng loại World Cup", 
                "Huấn luyện viên trưởng và các cầu thủ đội tuyển quốc gia Việt Nam đã có buổi tập chiến thuật quan trọng cuối cùng trước khi hành quân sang nước bạn. Tinh thần toàn đội đang lên rất cao sau những chiến thắng gần đây tại các giải đấu khu vực. Mục tiêu của đội là giành trọn 3 điểm để duy trì vị thế dẫn đầu bảng đấu.", 
                "https://images.unsplash.com/photo-1574629810360-7efbbe195018?q=80&w=600", 
                "24/06/2026"),
            News(2, 
                "Siêu sao cập bến giải vô địch quốc gia", 
                "Thị trường chuyển nhượng bóng đá nội đang nóng hơn bao giờ hết với sự xuất hiện của một tiền đạo lừng danh từng thi đấu tại châu Âu. Bản hợp đồng kỷ lục này được kỳ vọng sẽ nâng tầm chất lượng giải đấu và thu hút thêm sự quan tâm của người hâm mộ khắp cả nước. Lễ ra mắt chính thức sẽ diễn ra vào cuối tuần này.", 
                "https://images.unsplash.com/photo-1508098682722-e99c43a406b2?q=80&w=600", 
                "23/06/2026"),
            News(3, 
                "Khởi tranh giải bóng đá học đường toàn quốc", 
                "Sáng nay, giải bóng đá dành cho học sinh trung học đã chính thức khai mạc với sự tham gia của 32 đội bóng xuất sắc nhất từ các tỉnh thành. Đây là sân chơi bổ ích giúp tìm kiếm và ươm mầm các tài năng trẻ cho bóng đá nước nhà. Trận chung kết dự kiến sẽ được tổ chức tại sân vận động quốc gia.", 
                "https://images.unsplash.com/photo-1551958219-acbc608c6377?q=80&w=600", 
                "22/06/2026"),
            News(4, 
                "Công nghệ VAR sẽ được áp dụng rộng rãi", 
                "Ban tổ chức giải vô địch quốc gia thông báo sẽ áp dụng công nghệ hỗ trợ trọng tài video (VAR) trong tất cả các trận đấu kể từ mùa giải tới. Đây là bước tiến lớn giúp đảm bảo tính công bằng và chính xác trong các quyết định quan trọng của trọng tài, giảm thiểu tối đa các tranh cãi không đáng có trên sân cỏ.", 
                "https://images.unsplash.com/photo-1522778119026-d647f0596c20?q=80&w=600", 
                "21/06/2026")
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
