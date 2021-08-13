package com.ticketswap.assessment.features.search.medialist

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ticketswap.assessment.R
import com.ticketswap.assessment.api.Action
import com.ticketswap.assessment.api.isLoading
import com.ticketswap.assessment.databinding.FragmentMediaListTabBinding
import com.ticketswap.assessment.databinding.InflaterSearchItemBinding
import com.ticketswap.assessment.features.search.SearchViewModel
import com.ticketswap.assessment.features.search.media.MediaFragment.Companion.EXTRA_MEDIA_ITEM
import com.ticketswap.assessment.models.Item
import com.ticketswap.assessment.utils.*

class MediaListTabFragment : Fragment(), MediaClickListener {

    private lateinit var binder: FragmentMediaListTabBinding

    private val mediaListAdapter by lazy { MediaListAdapter(this) }

    private val searchViewModel: SearchViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    private val mediaType by lazy {
        requireArguments().getSerializable(EXTRA_MEDIA_TYPE) as MediaType
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binder = FragmentMediaListTabBinding.inflate(inflater, container, false)
        return binder.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (isInLayout) {
            outState.putParcelable(
                SEARCH_LIST_STATE,
                binder.root.layoutManager?.onSaveInstanceState()
            )
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (isInLayout) {
            savedInstanceState?.getParcelable<Parcelable>(SEARCH_LIST_STATE).let {
                binder.root.layoutManager?.onRestoreInstanceState(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binder.root.apply {
            this.adapter = mediaListAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }

        val latch = object : LoaderLatch {
            override fun canLoadMore() = !searchViewModel.searchResult.value.isLoading()
        }
        binder.root.loadIfLessThan(latch) {
            searchViewModel.loadMore(mediaType)
        }

        binder.root.addScrollStateChangedListener { _, _ ->
            binder.root.requestFocus()
            binder.root.hideKeyboard()
        }

        searchViewModel.searchResult.observe(viewLifecycleOwner, {
            if (it.action == Action.LOADING) {
                mediaListAdapter.showLoading(true)
            } else if (it.action == Action.FAILED) {
                mediaListAdapter.showLoading(false)
            }
        })

        searchViewModel.subscribeTo(mediaType).observeOnce(viewLifecycleOwner, {
            val immediateHideLoader =
                it.size == mediaListAdapter.getData().size || mediaListAdapter.getData().isEmpty()
            mediaListAdapter.setList(it) {
                if (immediateHideLoader) {
                    mediaListAdapter.showLoading(false)
                } else {
                    DataChangeListener.once(mediaListAdapter) { mediaListAdapter.showLoading(false) }
                }
            }
        })
    }

    companion object {
        private const val EXTRA_MEDIA_TYPE = "extra_media_type"
        private const val SEARCH_LIST_STATE = "search_list_state"
        fun forType(type: MediaType): MediaListTabFragment {
            val bundle = Bundle().apply { putSerializable(EXTRA_MEDIA_TYPE, type) }
            return MediaListTabFragment().apply { arguments = bundle }
        }
    }

    enum class MediaType(val value: String) {
        TRACK("track"), ARTIST("artist")
    }

    override fun onClickMedia(binder: InflaterSearchItemBinding, item: Item) {
        val controller = findNavController()

        val extras = FragmentNavigatorExtras(
            binder.icon to binder.icon.transitionName
        )
        val args = Bundle().apply { putSerializable(EXTRA_MEDIA_ITEM, item) }

        controller.navigate(R.id.trackFragment, args, null, extras)
    }

}