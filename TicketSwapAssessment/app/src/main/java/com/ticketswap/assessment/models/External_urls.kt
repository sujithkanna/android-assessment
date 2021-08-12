package com.ticketswap.assessment.models

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class External_urls(
    val spotify: String
):Serializable