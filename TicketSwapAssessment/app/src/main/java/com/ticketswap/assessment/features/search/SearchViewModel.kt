package com.ticketswap.assessment.features.search

import android.os.Parcelable
import androidx.lifecycle.*
import com.ticketswap.assessment.features.search.medialist.MediaListTabFragment.*
import com.ticketswap.assessment.features.search.medialist.MediaListTabFragment.MediaType.*
import com.ticketswap.assessment.models.Item
import com.ticketswap.assessment.utils.hookToResponse
import com.ticketswap.assessment.utils.livedata.AccumulatedListLivedata
import com.ticketswap.assessment.utils.throttleLatest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repositoryImpl: SearchRepository) :
    ViewModel() {

    fun isLoggedIn() = repositoryImpl.isLoggedIn()

    var scrollState: Parcelable? = null

    // Artist list will be added here
    private val _artistList = AccumulatedListLivedata<Item>()

    // Track list will be added here
    private val _tracksList = AccumulatedListLivedata<Item>()

    private val searchTriggerLive by lazy {
        SearchPaginatedLiveData(setOf(ARTIST, TRACK))
    }

    val searchResult = Transformations.switchMap(searchTriggerLive) { query ->
        repositoryImpl.searchSong(query).hookToResponse {
            val accumulate = query.offset != 0
            if (query.type.contains(TRACK)) {
                processSearchResult(TRACK, it.tracks?.items, accumulate, _tracksList)
            }
            if (query.type.contains(ARTIST)) {
                processSearchResult(ARTIST, it.artists?.items, accumulate, _artistList)
            }
        }
    }

    private fun processSearchResult(
        type: MediaType, items: List<Item>?,
        accumulate: Boolean, liveData: AccumulatedListLivedata<Item>
    ) {
        if (items.isNullOrEmpty()) {
            searchTriggerLive.finished(type)
            liveData.trigger()
        } else {
            liveData.setValue(items, accumulate)
            searchTriggerLive.addNewOffset(type, items.size)
        }
    }

    fun search(query: String)  {
        if (query.isNotEmpty()) {
            searchTriggerLive.search(query)
        } else {
            _artistList.clear()
            _tracksList.clear()
        }
    }

    fun subscribeTo(type: MediaType) = when (type) {
        TRACK -> _tracksList
        ARTIST -> _artistList
    }

    fun loadMore(mediaType: MediaType) = searchTriggerLive.nextPage(mediaType)

    companion object {
        const val TEXT_CHANGE_DEBOUNCE = 300L
    }

    enum class Mode {
        SEARCH, HISTORY
    }

}