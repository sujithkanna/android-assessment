package com.ticketswap.assessment.api

import com.ticketswap.assessment.di.SpotifyModule.Companion.NAMED_SPOTIFY_OKHTTP_CLIENT
import com.ticketswap.assessment.models.SearchResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Named

class SpotifyApi @Inject constructor(
    @Named(NAMED_SPOTIFY_OKHTTP_CLIENT) client: OkHttpClient
) : HttpBuilder(client, SPOTIFY_API) {

    suspend fun search(query: String, type: String = SEARCH_TYPE): SearchResponse? {
        val url: HttpUrl = newHttpBuilder("search")
            .addQueryParameter("q", query)
            .addQueryParameter("type", type)
            .build()
        val request = newRequest().url(url).build()
        return execute<SearchResponse>(request)
    }

    companion object {
        const val SEARCH_TYPE = "track,artist"
        const val SPOTIFY_API = "https://api.spotify.com/v1/"
    }
}