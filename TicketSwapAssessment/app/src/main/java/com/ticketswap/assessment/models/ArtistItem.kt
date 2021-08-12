package com.ticketswap.assessment.models

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class ArtistItem(
    val external_urls: External_urls,
    val followers: Followers,
    val genres: List<String>,
    val href: String,
    val id: String,
    val images: List<Images>,
    val name: String,
    val popularity: Int,
    val type: String,
    val uri: String
) : Serializable