package com.ticketswap.assessment.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ticketswap.assessment.models.SearchResponse

class SearchResponseLivedata() : MediatorLiveData<SearchResponse>() {

    fun addSource(livedata: LiveData<SearchResponse>) {
        addSource(livedata) { response -> }
    }
}