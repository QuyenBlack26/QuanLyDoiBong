package com.example.quanli.model

data class Match(
    val id: String,
    val tournamentId: String,
    val homeTeam: String,
    val awayTeam: String,
    val matchDate: String?,
    val stadium: String?,
    val homeScore: Int,
    val awayScore: Int,
    val status: String
)
