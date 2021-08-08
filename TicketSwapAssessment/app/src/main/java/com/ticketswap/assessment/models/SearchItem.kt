package com.ticketswap.assessment.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchItem(
    val href: String?,
    val items: List<Item>?,
    val limit: Int?,
    val next: String,
    val offset: Int?,
    val previous: String?,
    val total: Int?
)