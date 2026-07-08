package com.example.quanli.model

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
)
