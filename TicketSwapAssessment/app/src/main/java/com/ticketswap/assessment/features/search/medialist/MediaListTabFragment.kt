package com.ticketswap.assessment.features.search.medialist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ticketswap.assessment.api.Action
import com.ticketswap.assessment.databinding.FragmentMediaListTabBinding
import com.ticketswap.assessment.features.search.SearchViewModel
import com.ticketswap.assessment.utils.addScrollStateChangedListener
import com.ticketswap.assessment.utils.hideKeyboard
import com.ticketswap.assessment.utils.loadIfLessThan
import com.ticketswap.assessment.utils.observeOnce

class MediaListTabFragment : Fragment() {

    private lateinit var binder: FragmentMediaListTabBinding

    private val mediaListAdapter by lazy { MediaListAdapter() }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder.root.apply {
            this.adapter = mediaListAdapter
            this.layoutManager = LinearLayoutManager(context)
        }

        binder.root.loadIfLessThan {
            searchViewModel.loadMore(mediaType)
        }

        binder.root.addScrollStateChangedListener { _, _ ->
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
            mediaListAdapter.setList(it) {
                mediaListAdapter.showLoading(false)
            }
        })
    }

    companion object {
        private const val EXTRA_MEDIA_TYPE = "extra_media_type"
        fun forType(type: MediaType): MediaListTabFragment {
            val bundle = Bundle().apply { putSerializable(EXTRA_MEDIA_TYPE, type) }
            return MediaListTabFragment().apply { arguments = bundle }
        }
    }

    enum class MediaType(val value: String) {
        ARTIST("artist"), TRACK("track")
    }

}