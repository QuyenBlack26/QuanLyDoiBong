package com.example.quanli.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quanli.R
import com.example.quanli.databinding.ItemPlayerBinding
import com.example.quanli.model.PlayerModel

/**
 * Adapter for displaying player items in a RecyclerView.
 */
class PlayerAdapter(
    private var players: List<PlayerModel>,
    private val onEditClick: (PlayerModel) -> Unit,
    private val onDeleteClick: (PlayerModel) -> Unit,
    private val onItemClick: (PlayerModel) -> Unit
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    inner class PlayerViewHolder(private val binding: ItemPlayerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(player: PlayerModel) {
            val context = binding.root.context
            binding.apply {
                tvPlayerName.text = player.name
                tvClub.text = context.getString(R.string.lbl_club_prefix, player.club)
                tvPosition.text = context.getString(R.string.lbl_position_prefix, player.position)
                tvNationality.text = context.getString(R.string.lbl_nationality_prefix, player.nationality)
                tvOtherInfo.text = context.getString(R.string.lbl_other_info_format, 
                    player.birthDate, player.height, player.weight)
                tvJerseyNumber.text = context.getString(R.string.lbl_jersey_prefix, player.jerseyNumber)
                ivPlayerAvatar.setImageResource(player.avatar)

                btnEdit.setOnClickListener { onEditClick(player) }
                btnDelete.setOnClickListener { onDeleteClick(player) }
                root.setOnClickListener { onItemClick(player) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val binding = ItemPlayerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(players[position])
    }

    override fun getItemCount(): Int = players.size

    /**
     * Updates the list of players and notifies the adapter.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newPlayers: List<PlayerModel>) {
        this.players = newPlayers
        notifyDataSetChanged()
    }
}
