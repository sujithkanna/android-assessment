package com.ticketswap.assessment.features.search.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.squareup.picasso.Picasso
import com.ticketswap.assessment.R
import com.ticketswap.assessment.databinding.FragmentTrackBinding
import com.ticketswap.assessment.features.search.medialist.resolveImageForItem
import com.ticketswap.assessment.utils.into
import com.ticketswap.assessment.utils.rounded
import com.ticketswap.assessment.widgets.transitions.RadiusTransition

class TrackFragment : MediaFragment() {

    private lateinit var binder: FragmentTrackBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        postponeEnterTransition()
        binder = FragmentTrackBinding.inflate(inflater, container, false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAnimations()
        val image = resolveImageForItem(mediaItem)
        if (image == null) {
            val icon = ContextCompat.getDrawable(requireContext(), R.drawable.spotify)
            binder.icon.setImageDrawable(icon?.rounded())
            startPostponedEnterTransition()
        } else {
            Picasso.with(context).load(resolveImageForItem(mediaItem))
                .into(bitmapLoad = {
                    binder.icon.setImageDrawable(it.rounded())
                    startPostponedEnterTransition()
                }, bitmapFailed = {
                    binder.icon.setImageDrawable(it)
                    startPostponedEnterTransition()
                })
        }
    }

    private fun setupAnimations() {
        postponeEnterTransition()
        ViewCompat.setTransitionName(binder.icon, "icon_${mediaItem.id}")
        sharedElementEnterTransition = RadiusTransition.toSquare(requireContext())
        sharedElementReturnTransition = RadiusTransition.toCircle(requireContext())
    }
}
