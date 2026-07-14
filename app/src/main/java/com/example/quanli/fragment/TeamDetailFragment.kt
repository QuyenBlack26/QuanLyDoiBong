package com.example.quanli.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quanli.R
import com.example.quanli.adapter.TeamMatchAdapter
import com.example.quanli.model.News
import com.example.quanli.model.Player
import com.example.quanli.model.TeamMatchItem
import com.google.android.material.tabs.TabLayout
import com.example.quanli.adapter.NewsAdapter
import com.example.quanli.adapter.PlayerAdapter
import com.example.quanli.model.PlayerModel
import com.example.quanli.model.ScheduleItem
import com.example.quanli.adapter.ScheduleAdapter

class TeamDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)
        setupTabs(view)
    }

    private fun setupRecyclerView(root: View) {
        val rv = root.findViewById<RecyclerView>(R.id.recyclerViewMatches)
        val mockData = listOf(
            TeamMatchItem.TournamentHeader("World Championship - Play Offs", "THẾ GIỚI"),
            TeamMatchItem.MatchResult("07.07.", "Bồ Đào Nha", "Pháp", 0, 0, TeamMatchItem.ResultType.DRAW),
            TeamMatchItem.MatchResult("02.07.", "Bồ Đào Nha", "Slovenia", 1, 0, TeamMatchItem.ResultType.WIN),
            TeamMatchItem.TournamentHeader("Chung kết Euro", "CHÂU ÂU"),
            TeamMatchItem.MatchResult("27.06.", "Georgia", "Bồ Đào Nha", 2, 0, TeamMatchItem.ResultType.LOSS),
            TeamMatchItem.MatchResult("22.06.", "Thổ Nhĩ Kỳ", "Bồ Đào Nha", 0, 3, TeamMatchItem.ResultType.WIN)
        )

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = TeamMatchAdapter(mockData)
    }

    private fun setupTabs(root: View) {
        val tabs = root.findViewById<TabLayout>(R.id.tabLayout)
        val rv = root.findViewById<RecyclerView>(R.id.recyclerViewMatches)

        // Lắp "máy nghe lén" sự kiện bấm Tab
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> rv.adapter = TeamMatchAdapter(getMockKetQua())
                    1 -> rv.adapter = ScheduleAdapter(getMockLichThiDau()) // Tạm khóa tab này vì tui chưa thấy Adapter Lịch Thi Đấu của ông
                    2 -> rv.adapter = NewsAdapter(
                        newsList = getMockDiemTin(),
                        onItemClick = {},
                        onItemLongClick = {}
                    )
                    3 -> rv.adapter = PlayerAdapter(
                        players = getMockDoiHinh(),
                        onItemClick = {},
                        onEditClick = {},
                        onDeleteClick = {} // <--- Thêm dấu đóng ngoặc vào ngay sau đây
                    ) // <--- Xóa dấu ở dòng 76 đi, vì nó đã nằm ở đây rồi
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    // --- CÁC HÀM TẠO DỮ LIỆU GIẢ CHO TỪNG TAB ---

    private fun getMockKetQua(): List<TeamMatchItem> {
        return listOf(
            TeamMatchItem.TournamentHeader("World Championship - Play Offs", "THẾ GIỚI"),
            TeamMatchItem.MatchResult("07.07.", "Bồ Đào Nha", "Pháp", 0, 0, TeamMatchItem.ResultType.DRAW),
            TeamMatchItem.MatchResult("02.07.", "Bồ Đào Nha", "Slovenia", 1, 0, TeamMatchItem.ResultType.WIN),
            TeamMatchItem.TournamentHeader("Chung kết Euro", "CHÂU ÂU"),
            TeamMatchItem.MatchResult("27.06.", "Georgia", "Bồ Đào Nha", 2, 0, TeamMatchItem.ResultType.LOSS),
            TeamMatchItem.MatchResult("22.06.", "Thổ Nhĩ Kỳ", "Bồ Đào Nha", 0, 3, TeamMatchItem.ResultType.WIN)
        )
    }
    private fun getMockLichThiDau(): List<ScheduleItem> {
        return listOf(
            ScheduleItem.Header("UEFA Nations League - League A", "CHÂU ÂU"),
            ScheduleItem.Match("25.09.", "Bồ Đào Nha", "Wales", "01:45"),
            ScheduleItem.Match("28.09.", "Na Uy", "Bồ Đào Nha", "01:45"),
            ScheduleItem.Match("02.10.", "Đan Mạch", "Bồ Đào Nha", "01:45"),
            ScheduleItem.Match("05.10.", "Bồ Đào Nha", "Na Uy", "01:45"),
            ScheduleItem.Match("15.11.", "Bồ Đào Nha", "Đan Mạch", "02:45"),
            ScheduleItem.Match("18.11.", "Wales", "Bồ Đào Nha", "02:45")
        )
    }
    private fun getMockDiemTin(): List<News> {
        return listOf(
            News(id = 1, title = "Pedri nói gì về khả năng Tây Ban Nha vô địch World Cup 2026?", content = "", imageUrl = "", date = "Bongda24h, Hôm qua"),
            News(id = 2, title = "Cristiano Ronaldo dự đoán về TBN ở World Cup 2026", content = "", imageUrl = "", date = "Bongda24h, Hôm qua"),
            News(id = 3, title = "Người đẹp “Bà Tưng” mặc áo Messi “trêu” Ronaldo...", content = "", imageUrl = "", date = "24h.com.vn, 07.07.2026"),
            News(id = 4, title = "Người hùng tuyển Tây Ban Nha Mikel Merino...", content = "", imageUrl = "", date = "Bongda24h, 07.07.2026")
        )
    }

    private fun getMockDoiHinh(): List<PlayerModel> {
        return listOf(
            PlayerModel(
                playerId = "1", name = "Costa Diogo", birthDate = "19/09/1999", nationality = "Bồ Đào Nha",
                position = "Thủ môn", height = 186, weight = 82, club = "Porto", jerseyNumber = 1, avatar = R.mipmap.ic_launcher
            ),
            PlayerModel(
                playerId = "12", name = "Sa Jose", birthDate = "17/01/1993", nationality = "Bồ Đào Nha",
                position = "Thủ môn", height = 192, weight = 84, club = "Wolves", jerseyNumber = 12, avatar = R.mipmap.ic_launcher
            ),
            PlayerModel(
                playerId = "22", name = "Silva Rui", birthDate = "07/02/1994", nationality = "Bồ Đào Nha",
                position = "Thủ môn", height = 191, weight = 91, club = "Betis", jerseyNumber = 22, avatar = R.mipmap.ic_launcher
            ),
            PlayerModel(
                playerId = "4", name = "Araujo Tomas", birthDate = "16/05/2002", nationality = "Bồ Đào Nha",
                position = "Hậu vệ", height = 187, weight = 80, club = "Benfica", jerseyNumber = 4, avatar = R.mipmap.ic_launcher
            ),
            PlayerModel(
                playerId = "20", name = "Cancelo Joao", birthDate = "27/05/1994", nationality = "Bồ Đào Nha",
                position = "Hậu vệ", height = 182, weight = 74, club = "Al Hilal", jerseyNumber = 20, avatar = R.mipmap.ic_launcher
            )
        )
    }
    // Ghi chú cho nhóm trưởng:
    // Khi nào có dữ liệu thật từ Database (MySQL/API), gọi hàm này và truyền danh sách vào đây!
    fun capNhatDuLieuTuDatabase(danhSachThat: List<TeamMatchItem>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerViewMatches)

        // Cập nhật bằng cách: Thay luôn một cái Adapter mới chứa dữ liệu thật
        recyclerView?.adapter = TeamMatchAdapter(danhSachThat)
    }
}