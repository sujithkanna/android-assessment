package com.ticketswap.assessment.features.search

import com.ticketswap.assessment.user.UserManager
import javax.inject.Inject

class SearchRepository @Inject constructor(private val userManager: UserManager) {

    fun isLoggedIn() = !userManager.spotifyAuthToken.isNullOrBlank()
}