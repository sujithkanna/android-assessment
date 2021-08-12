package com.ticketswap.assessment.models

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Followers(
    val href: String?,
    val total: Int
) : Serializable