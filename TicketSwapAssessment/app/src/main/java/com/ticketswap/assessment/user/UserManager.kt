package com.ticketswap.assessment.user

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class UserManager @Inject constructor(private val prefs: SharedPreferences) {

    var spotifyAuthToken: String?
        get() = prefs.getString(PREFS_SPOTIFY_AUTH_TOKEN, null)
        set(value) = prefs.edit { putString(PREFS_SPOTIFY_AUTH_TOKEN, value) }

    companion object {
        const val PREFS_SPOTIFY_AUTH_TOKEN = "prefs_spotify_auth_token"
    }

}