package com.ticketswap.assessment.features.search.media

import androidx.lifecycle.MutableLiveData
import com.ticketswap.assessment.features.search.medialist.TrackViewHolder
import com.ticketswap.assessment.models.Item
import com.ticketswap.assessment.utils.asLiveData
import com.ticketswap.assessment.utils.toTimerString

class TrackViewModel : MediaViewModel() {

    private val _detailsList = MutableLiveData<List<Media>>()
    val detailsList = _detailsList.asLiveData()

    override fun onUpdateViewMedia(item: Item) {
        _detailsList.value = prepareInfoList(item)
    }

    private fun prepareInfoList(item: Item): List<Media> {
        val details = arrayListOf<Media>()
        details.add(Media("Album", item.album?.name ?: "Unknown"))
        val artists = TrackViewHolder.getArtists(item)
        if (!artists.isNullOrEmpty()) {
            details.add(Media("Artist", artists.joinToString(", ")))
        }
        val duration = item.duration_ms.toTimerString()
        if (duration != null) {
            details.add(Media("Duration", duration))
        }
        return details
    }

}