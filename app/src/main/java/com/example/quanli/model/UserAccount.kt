package com.example.quanli.model

data class UserAccount(
    val id: Int,
    val username: String,
    val email: String?,
    val fullName: String?,
    val role: String
)
