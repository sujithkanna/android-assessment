package com.ticketswap.assessment.spotify

data class Artists(
    val href: String,
    val items: List<Items>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: String,
    val total: Int
)