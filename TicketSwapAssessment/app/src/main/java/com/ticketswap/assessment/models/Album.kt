package com.ticketswap.assessment.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Album(
    val album_type: String,
    val artists: List<Artists>,
    val available_markets: List<String>,
    val external_urls: External_urls,
    val href: String,
    val id: String,
    val images: List<Images>,
    val name: String,
    val release_date: String,
    val release_date_precision: String,
    val total_tracks: Int,
    val type: String,
    val uri: String
)