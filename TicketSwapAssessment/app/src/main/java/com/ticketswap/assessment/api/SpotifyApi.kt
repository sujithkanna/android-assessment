package com.ticketswap.assessment.api

import com.ticketswap.assessment.spotify.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface SpotifyApi {
    @GET("search")
    fun searchSpotify(@Query("q") query: String, @Query("type") type: String): Single<SearchResponse>
}