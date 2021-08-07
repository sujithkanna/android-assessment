package com.ticketswap.assessment.spotify

data class Items(
    val external_urls: Map<String, String>,
    val genres: List<String>,
    val href: String,
    val id: String,
    val images: List<Images>,
    val name: String,
    val popularity: Int,
    val type: String,
    val uri: String
)