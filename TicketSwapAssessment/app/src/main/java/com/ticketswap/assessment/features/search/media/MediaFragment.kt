package com.ticketswap.assessment.features.search.media

import androidx.fragment.app.Fragment
import com.ticketswap.assessment.models.Item

abstract class MediaFragment: Fragment() {

    protected val mediaItem by lazy {
        requireArguments().getSerializable(EXTRA_MEDIA_ITEM) as Item
    }

    companion object {
        const val EXTRA_MEDIA_ITEM = "extra_medium_item"
    }
}