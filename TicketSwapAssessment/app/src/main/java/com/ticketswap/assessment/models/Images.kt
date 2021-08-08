package com.ticketswap.assessment.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Images (
	val height : Int?,
	val url : String?,
	val width : Int?
)