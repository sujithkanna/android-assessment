package com.ticketswap.assessment.features.search

import com.ticketswap.assessment.api.Resource
import com.ticketswap.assessment.api.SpotifyApi
import com.ticketswap.assessment.api.networkBoundResource
import com.ticketswap.assessment.models.Item
import com.ticketswap.assessment.user.UserManager
import com.ticketswap.assessment.utils.append
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchRepository @Inject constructor(
    private val userManager: UserManager,
    private val spotifyApi: SpotifyApi
) {

    fun isLoggedIn() = !userManager.spotifyAuthToken.isNullOrBlank()

    fun searchSong(query: String): Flow<Resource<out List<Item>?>> = networkBoundResource(
        fetch = { spotifyApi.search(query) },
        query = { null },
        saveFetchResult = { response -> response.artists?.items.append(response.tracks?.items) }
    )
}