package gob.pe.msi.trakingrealtime.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class SessionManager(context: Context) {
/*
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()
    private val gson = Gson()

    fun saveUserSession(user: User, rememberMe: Boolean) {
        val userJson = gson.toJson(user)
        editor.putString("user", userJson)
        editor.putBoolean("logged_in", true)
        if (rememberMe) {
            val currentTime = System.currentTimeMillis()
            editor.putLong("session_expiry_time", currentTime + (30 * 24 * 60 * 60 * 1000)) // 30 días
        } else {
            editor.putLong("session_expiry_time", 0L)
        }
        editor.apply()
    }

    fun getUserSession(): User? {
        val userJson = prefs.getString("user", null) ?: return null
        return gson.fromJson(userJson, User::class.java)
    }

    fun isLoggedIn(): Boolean {
        val loggedIn = prefs.getBoolean("logged_in", false)
        if (loggedIn) {
            val expiryTime = prefs.getLong("session_expiry_time", 0L)
            if (expiryTime == 0L || System.currentTimeMillis() < expiryTime) {
                return true
            } else {
                clearSession()
            }
        }
        return false
    }

    fun clearSession() {
        editor.clear()
        editor.apply()
    }*/
}