package com.example.quanli.model

sealed class TeamMatchItem {
    data class TournamentHeader(
        val name: String,
        val region: String
    ) : TeamMatchItem()

    data class MatchResult(
        val date: String,
        val homeTeam: String,
        val awayTeam: String,
        val homeScore: Int,
        val awayScore: Int,
        val resultStatus: ResultType // T, H, B
    ) : TeamMatchItem()

    enum class ResultType {
        WIN, DRAW, LOSS
    }
}
