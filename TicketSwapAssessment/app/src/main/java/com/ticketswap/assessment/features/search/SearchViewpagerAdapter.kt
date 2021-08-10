package com.ticketswap.assessment.features.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ticketswap.assessment.features.search.medialist.MediaListTabFragment

class SearchViewpagerAdapter(
    fragment: Fragment, private val types: Array<MediaListTabFragment.MediaType>
) : FragmentStateAdapter(fragment) {


    override fun getItemCount() = types.size

    override fun createFragment(position: Int) = MediaListTabFragment.forType(types[position])
}