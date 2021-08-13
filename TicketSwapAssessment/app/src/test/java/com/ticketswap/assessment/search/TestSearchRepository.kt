package com.ticketswap.assessment.search

import com.ticketswap.assessment.api.Resource
import com.ticketswap.assessment.features.search.SearchQuery
import com.ticketswap.assessment.features.search.SearchRepository
import com.ticketswap.assessment.models.SearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class TestSearchRepository : SearchRepository() {

    override fun isLoggedIn() = false

    override fun searchSong(query: SearchQuery): Flow<Resource<out SearchResponse?>> = flow { }

}