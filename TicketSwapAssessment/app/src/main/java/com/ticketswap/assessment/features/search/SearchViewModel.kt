package com.ticketswap.assessment.features.search

import androidx.lifecycle.*
import com.ticketswap.assessment.utils.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel() {

    fun isLoggedIn() = repository.isLoggedIn()

    private val searchTrigger = MutableLiveData<String>()

    val searchResult = Transformations.switchMap(searchTrigger) { query ->
        return@switchMap repository.searchSong(query).asLiveData()
    }

    fun search(query: String) {
        searchTrigger.value = query
    }

}