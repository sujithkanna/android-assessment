package com.ticketswap.assessment.models

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class SearchResponse(
    val artists: Artists?,
    val tracks: Tracks?
) : Serializable