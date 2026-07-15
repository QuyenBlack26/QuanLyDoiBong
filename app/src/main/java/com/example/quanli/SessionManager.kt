package com.example.quanli

import android.content.Context
import android.content.SharedPreferences
import com.example.quanli.model.UserAccount

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "UserSession"
        private const val KEY_USERNAME = "username"
        private const val KEY_ROLE = "role"
        private const val KEY_FULLNAME = "fullname"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
    }

    fun saveUser(user: UserAccount) {
        val editor = prefs.edit()
        editor.putString(KEY_USERNAME, user.username)
        editor.putString(KEY_ROLE, user.role)
        editor.putString(KEY_FULLNAME, user.fullName)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.apply()
    }

    fun getUsername(): String? = prefs.getString(KEY_USERNAME, null)
    fun getRole(): String? = prefs.getString(KEY_ROLE, "user")
    fun getFullName(): String? = prefs.getString(KEY_FULLNAME, null)
    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)

    fun logout() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}
