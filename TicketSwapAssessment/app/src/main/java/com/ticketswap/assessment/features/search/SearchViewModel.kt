package com.ticketswap.assessment.features.search

import androidx.lifecycle.*
import com.ticketswap.assessment.utils.throttleLatest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel() {

    fun isLoggedIn() = repository.isLoggedIn()

    private val searchDebounce by lazy {
        throttleLatest<String>(TEXT_CHANGE_DEBOUNCE, viewModelScope) {
            if (it.isNotEmpty()) searchTrigger.value = it
        }
    }

    private val searchTrigger = MutableLiveData<String>()

    val searchResult = Transformations.switchMap(searchTrigger) { query ->
        return@switchMap repository.searchSong(query).asLiveData()
    }

    fun search(query: String) = searchDebounce.invoke(query)

    companion object {
        const val TEXT_CHANGE_DEBOUNCE = 300L
    }

    enum class Mode {
        SEARCH, HISTORY
    }

}