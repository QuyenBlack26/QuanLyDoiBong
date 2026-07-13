package com.example.quanli.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quanli.R
import com.example.quanli.model.ScheduleItem

class ScheduleAdapter(private val items: List<ScheduleItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_MATCH = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ScheduleItem.Header -> TYPE_HEADER
            is ScheduleItem.Match -> TYPE_MATCH
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tournament_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
            MatchViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ScheduleItem.Header -> (holder as HeaderViewHolder).bind(item)
            is ScheduleItem.Match -> (holder as MatchViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Lưu ý: Nếu chữ tvTournamentName báo đỏ, ông check lại file item_tournament_header.xml
        // xem tên ID của cái TextView tiêu đề là gì rồi sửa lại cho khớp nha.
        private val tvName = view.findViewById<TextView>(R.id.tvTournamentName)
        fun bind(item: ScheduleItem.Header) {
            tvName?.text = item.tournamentName
        }
    }

    class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvDate = view.findViewById<TextView>(R.id.tvScheduleDate)
        private val tvHomeTeam = view.findViewById<TextView>(R.id.tvHomeTeam)
        private val tvAwayTeam = view.findViewById<TextView>(R.id.tvAwayTeam)
        private val tvTime = view.findViewById<TextView>(R.id.tvScheduleTime)

        fun bind(item: ScheduleItem.Match) {
            tvDate.text = item.date
            tvHomeTeam.text = item.homeTeam
            tvAwayTeam.text = item.awayTeam
            tvTime.text = item.time
        }
    }
}