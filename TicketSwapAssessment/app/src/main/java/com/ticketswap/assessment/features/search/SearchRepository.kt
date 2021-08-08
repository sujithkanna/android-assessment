package com.ticketswap.assessment.features.search

import com.ticketswap.assessment.api.SpotifyApi
import com.ticketswap.assessment.api.networkBoundResource
import com.ticketswap.assessment.user.UserManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


class SearchRepository @Inject constructor(
    private val userManager: UserManager,
    private val spotifyApi: SpotifyApi
) {

    fun isLoggedIn() = !userManager.spotifyAuthToken.isNullOrBlank()

    fun searchSong(query: String) = networkBoundResource(
        fetch = { spotifyApi.search(query) },
        query = { null },
        saveFetchResult = { response ->

        }
    )
}