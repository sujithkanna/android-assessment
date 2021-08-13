package com.ticketswap.assessment.features.search.media

import androidx.lifecycle.ViewModel
import com.ticketswap.assessment.models.Item

abstract class MediaViewModel : ViewModel() {

    protected lateinit var item: Item

    fun setMediaItem(item: Item) {
        this.item = item
        onUpdateViewMedia(item)
    }

    abstract fun onUpdateViewMedia(item: Item)

}