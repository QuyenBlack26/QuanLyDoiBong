package com.example.quanli.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.quanli.R
import com.example.quanli.model.TeamMatchItem

class TeamMatchAdapter(private val items: List<TeamMatchItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_MATCH = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is TeamMatchItem.TournamentHeader -> TYPE_HEADER
            is TeamMatchItem.MatchResult -> TYPE_MATCH
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_tournament_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_match_result, parent, false)
            MatchViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if ((holder is HeaderViewHolder) && (item is TeamMatchItem.TournamentHeader)) {
            holder.tvName.text = item.name
            holder.tvRegion.text = item.region
        } else if ((holder is MatchViewHolder) && (item is TeamMatchItem.MatchResult)) {
            holder.tvDate.text = item.date
            holder.tvHomeName.text = item.homeTeam
            holder.tvAwayName.text = item.awayTeam
            holder.tvHomeScore.text = item.homeScore.toString()
            holder.tvAwayScore.text = item.awayScore.toString()

            // Highlight winner
            if (item.homeScore > item.awayScore) {
                holder.tvHomeName.setTypeface(null, Typeface.BOLD)
                holder.tvAwayName.setTypeface(null, Typeface.NORMAL)
            } else if (item.awayScore > item.homeScore) {
                holder.tvAwayName.setTypeface(null, Typeface.BOLD)
                holder.tvHomeName.setTypeface(null, Typeface.NORMAL)
            } else {
                holder.tvHomeName.setTypeface(null, Typeface.NORMAL)
                holder.tvAwayName.setTypeface(null, Typeface.NORMAL)
            }

            // Set badge
            val (badgeText, colorRes) = when (item.resultStatus) {
                TeamMatchItem.ResultType.WIN -> "T" to R.color.win_green
                TeamMatchItem.ResultType.DRAW -> "H" to R.color.draw_yellow
                TeamMatchItem.ResultType.LOSS -> "B" to R.color.loss_red
            }
            holder.tvBadgeText.text = badgeText
            holder.cardBadge.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, colorRes))
        }
    }

    override fun getItemCount(): Int = items.size

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvTournamentName)
        val tvRegion: TextView = view.findViewById(R.id.tvTournamentRegion)
    }

    class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDate: TextView = view.findViewById(R.id.tvMatchDateResult)
        val tvHomeName: TextView = view.findViewById(R.id.tvHomeTeamResult)
        val tvAwayName: TextView = view.findViewById(R.id.tvAwayTeamResult)
        val tvHomeScore: TextView = view.findViewById(R.id.tvScoreHomeResult)
        val tvAwayScore: TextView = view.findViewById(R.id.tvScoreAwayResult)
        val tvBadgeText: TextView = view.findViewById(R.id.tvResultStatusText)
        val cardBadge: CardView = view.findViewById(R.id.cardResultBadge)
    }
}
