package com.ticketswap.assessment.features.login

import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.ticketswap.assessment.user.UserManager
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val spotifyAuth: AuthenticationRequest, private val userManager: UserManager
) {

    fun getAuthRequest() = spotifyAuth

    fun saveAccessToken(accessToken: String) {
        userManager.spotifyAuthToken = accessToken
    }

    fun isLoggedIn() = userManager.spotifyAuthToken != null
}