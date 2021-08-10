package com.ticketswap.assessment.features.search

import com.ticketswap.assessment.api.Resource
import com.ticketswap.assessment.api.SpotifyApi
import com.ticketswap.assessment.api.networkBoundResource
import com.ticketswap.assessment.models.SearchResponse
import com.ticketswap.assessment.user.UserManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchRepository @Inject constructor(
    private val userManager: UserManager,
    private val spotifyApi: SpotifyApi
) {

    fun isLoggedIn() = !userManager.spotifyAuthToken.isNullOrBlank()

    @Throws(Exception::class)
    fun searchSong(query: SearchQuery): Flow<Resource<out SearchResponse?>> =
        networkBoundResource(
            fetch = { spotifyApi.search(query.offset, query.query,
                query.type.joinToString(",") { it.value }) },
            query = { null },
            saveFetchResult = { response -> response }
        )
}