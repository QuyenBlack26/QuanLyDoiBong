package com.example.quanli.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quanli.R
import com.example.quanli.model.Player

class PlayerAdapter(private var players: List<Player>) :
    RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.playerName)
        val position: TextView = view.findViewById(R.id.playerPosition)
        val details: TextView = view.findViewById(R.id.playerDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        holder.name.text = player.name
        holder.position.text = player.position ?: "N/A"
        holder.details.text = "${player.height}cm - ${player.weight}kg | Quốc tịch: ${player.nationality}"
    }

    override fun getItemCount() = players.size

    fun updateData(newPlayers: List<Player>) {
        players = newPlayers
        notifyDataSetChanged()
    }
}
