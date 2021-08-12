package com.ticketswap.assessment.models

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Item(
    val album: Album?,
    val artists: List<Artists>?,
    val available_markets: List<String>?,
    val disc_number: Int?,
    val duration_ms: Long?,
    val explicit: Boolean?,
    val external_ids: External_ids?,
    val external_urls: External_urls?,
    val href: String,
    val id: String,
    val is_local: Boolean?,
    val name: String,
    val popularity: Int?,
    val preview_url: String?,
    val track_number: Int?,
    val type: String,
    val uri: String?,
    val followers: Followers?,
    val genres: List<String>?,
    val images: List<Images>?,
) : Serializable