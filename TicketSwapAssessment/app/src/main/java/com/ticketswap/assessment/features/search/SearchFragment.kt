package com.ticketswap.assessment.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.ticketswap.assessment.R
import com.ticketswap.assessment.api.Action
import com.ticketswap.assessment.databinding.FragmentSearchBinding
import com.ticketswap.assessment.features.search.medialist.MediaListTabFragment
import com.ticketswap.assessment.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binder: FragmentSearchBinding

    private val navController by lazy { findNavController() }

    private val searchViewModel: SearchViewModel by viewModels()

    private val types by lazy {
        MediaListTabFragment.MediaType.values()
    }

    private val backToExit by lazy {
        doubleBackExitStrategy(lifecycleScope) {
            when (it) {
                BackPress.LAST -> requireActivity().finish()
                BackPress.FIRST -> showToast(R.string.press_again_to_exit, Toast.LENGTH_SHORT)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?): View {
        binder = FragmentSearchBinding.inflate(inflater, parent, false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        listenForBackPress(BackPressAction.create { backToExit.invoke() })

        binder.searchField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binder.searchAppbar.setExpanded(false, true)
        }

        binder.searchField.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            searchViewModel.search(text.toString())
        })

        binder.searchViewpager.adapter = SearchViewpagerAdapter(this, types)

        binder.searchViewpager.doOnPreDraw {
            startPostponedEnterTransition()
        }

        TabLayoutMediator(binder.tabLayout, binder.searchViewpager) { tab, position ->
            tab.text = types[position].value.initialtCaps()
        }.attach()

        searchViewModel.searchResult.observe(viewLifecycleOwner, {
            if (it.action == Action.UNAUTHORISED) {
                navController.navigate(R.id.loginFragment)
            }
        })

        if (!searchViewModel.isLoggedIn()) {
            navController.navigate(R.id.loginFragment)
        }
    }

}