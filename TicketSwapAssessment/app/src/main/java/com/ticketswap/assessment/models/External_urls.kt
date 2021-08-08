package com.ticketswap.assessment.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class External_urls(
    val spotify: String
)