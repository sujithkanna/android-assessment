package com.ticketswap.assessment.features.search.media

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ticketswap.assessment.models.Item
import com.ticketswap.assessment.utils.asLiveData

abstract class MediaViewModel : ViewModel() {

    protected val _detailsList = MutableLiveData<List<Media>>()
    val detailsList = _detailsList.asLiveData()

    protected lateinit var item: Item

    fun setMediaItem(item: Item) {
        this.item = item
        onUpdateViewMedia(item)
    }

    abstract fun onUpdateViewMedia(item: Item)

}