package com.example.quanli.model

import com.example.quanli.R

/**
 * Data model for a Player in the Football Club.
 */
data class PlayerModel(
    val playerId: String,
    val name: String,
    val birthDate: String,
    val nationality: String,
    val position: String,
    val height: Int,
    val weight: Int,
    val club: String,
    val jerseyNumber: Int,
    val avatar: Int
) {
    companion object {
        fun fromPlayer(player: Player, club: String = "N/A", jerseyNumber: Int = 0, avatar: Int = R.drawable.ic_player_placeholder): PlayerModel {
            return PlayerModel(
                playerId = player.id,
                name = player.name,
                birthDate = player.birthDate ?: "",
                nationality = player.nationality ?: "",
                position = player.position ?: "",
                height = player.height ?: 0,
                weight = player.weight ?: 0,
                club = club,
                jerseyNumber = jerseyNumber,
                avatar = avatar
            )
        }
    }
}
