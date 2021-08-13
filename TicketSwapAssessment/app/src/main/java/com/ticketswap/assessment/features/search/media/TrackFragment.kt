package com.ticketswap.assessment.features.search.media

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.ticketswap.assessment.R
import com.ticketswap.assessment.databinding.FragmentTrackBinding
import com.ticketswap.assessment.features.search.medialist.resolveImageForItem
import com.ticketswap.assessment.utils.TransitionListener
import com.ticketswap.assessment.utils.dpToPx
import com.ticketswap.assessment.utils.into
import com.ticketswap.assessment.utils.rounded
import com.ticketswap.assessment.widgets.MarginDecoration
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
        loadCoverImage()

        val mediaAdapter = MediaDetailsAdapter()
        binder.name.text = mediaItem.name
        binder.details.apply {
            adapter = mediaAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(MarginDecoration(25.dpToPx().toInt()))
        }

        viewModel.detailsList.observe(viewLifecycleOwner, {
            mediaAdapter.setDetails(it)
        })

        viewModel.setMediaItem(mediaItem)

        if (savedInstanceState != null) revealDetails()
    }

    private fun loadCoverImage() {
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

        sharedElementEnterTransition = RadiusTransition.toSquare(requireContext()).apply {
            addListener(TransitionListener(
                onTransitionEnd = { revealDetails() }
            ))
        }
        sharedElementReturnTransition = RadiusTransition.toCircle(requireContext())
    }

    private fun revealDetails() {
        ObjectAnimator.ofFloat(binder.play, View.ALPHA, 0f, 1f).start()
        ObjectAnimator.ofFloat(binder.name, View.ALPHA, 0f, 1f).start()
        ObjectAnimator.ofFloat(binder.details, View.ALPHA, 0f, 1f).start()
        ObjectAnimator.ofFloat(binder.nameBackground, View.ALPHA, 0f, 1f).start()
    }
}
