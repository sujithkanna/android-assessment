package com.ticketswap.assessment.features.search

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.ticketswap.assessment.features.search.medialist.MediaListTabFragment.MediaType

class SearchPaginatedLiveData(private val supports: Set<MediaType>) :
    MediatorLiveData<SearchQuery>() {

    private var currentQuery: String = ""
    private val pages = HashMap<MediaType, OffsetLiveData>().apply {
        createPages(this)
    }

    private fun createPages(hashMap: HashMap<MediaType, OffsetLiveData>) {
        supports.forEach { type ->
            hashMap[type] = createSource(type)
        }
    }

    private fun createSource(type: MediaType) = OffsetLiveData.create(type).also { liveData ->
        this.addSource(liveData) { page ->
            if (page == null) return@addSource
            this.value = SearchQuery(currentQuery, page.offset, setOf(page.type))
        }
    }

    fun finished(type: MediaType) = pages[type]?.markAsFinished()

    fun search(query: String) {
        if (currentQuery == query) return
        this.currentQuery = query
        pages.values.forEach { it.reset() }
        value = SearchQuery(query, 0, supports)
    }

    fun nextPage(type: MediaType) = pages[type]?.loadMore()

    fun addNewOffset(type: MediaType, offset: Int) = pages[type]?.addOffset(offset)

}

private class OffsetLiveData(private val page: SearchPage) : MutableLiveData<SearchPage?>() {

    private var active = true

    fun reset() {
        active = true
        page.offset = 0
    }

    fun markAsFinished() = run { active = false }

    fun loadMore() = run { value = page }

    fun addOffset(offset: Int) = run { page.offset += offset }

    override fun setValue(value: SearchPage?) {
        if (active && value != null) super.setValue(value)
    }

    companion object {
        fun create(type: MediaType): OffsetLiveData {
            return OffsetLiveData(SearchPage(type))
        }
    }
}

/**
 * Data class handled by the SearchPaginatedLiveData
 */
data class SearchPage(val type: MediaType, var offset: Int = 0)

data class SearchQuery(val query: String, val offset: Int, val type: Set<MediaType>)
