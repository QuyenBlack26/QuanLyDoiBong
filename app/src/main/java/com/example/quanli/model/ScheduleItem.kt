package com.example.quanli.model

sealed class ScheduleItem {
    data class Header(val tournamentName: String, val region: String) : ScheduleItem()
    data class Match(val date: String, val homeTeam: String, val awayTeam: String, val time: String) : ScheduleItem()
}