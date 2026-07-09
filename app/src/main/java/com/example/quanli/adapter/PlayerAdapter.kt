package com.example.quanli.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quanli.R
import com.example.quanli.databinding.ItemPlayerBinding
import com.example.quanli.model.PlayerModel

class PlayerAdapter(
    private var players: List<PlayerModel>,
    private val onEditClick: (PlayerModel) -> Unit = {},
    private val onDeleteClick: (PlayerModel) -> Unit = {},
    private val onItemClick: (PlayerModel) -> Unit = {}
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    class PlayerViewHolder(val binding: ItemPlayerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val binding = ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        val context = holder.binding.root.context
        holder.binding.apply {
            tvPlayerName.text = player.name
            tvPlayerId.text = player.playerId
            tvPlayerPosition.text = context.getString(R.string.lbl_position_prefix, player.position)
            tvPlayerClub.text = context.getString(R.string.lbl_club_prefix, player.club)
            tvPlayerNumber.text = context.getString(R.string.lbl_jersey_prefix, player.jerseyNumber)
            ivPlayerAvatar.setImageResource(player.avatar)

            btnEditPlayer.setOnClickListener { onEditClick(player) }
            btnDeletePlayer.setOnClickListener { onDeleteClick(player) }
            root.setOnClickListener { onItemClick(player) }
        }
    }

    override fun getItemCount(): Int = players.size

    fun updateData(newPlayers: List<PlayerModel>) {
        players = newPlayers
        notifyDataSetChanged()
    }
}
