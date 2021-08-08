package com.ticketswap.assessment.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.ticketswap.assessment.R
import com.ticketswap.assessment.api.Action
import com.ticketswap.assessment.databinding.FragmentSearchBinding
import com.ticketswap.assessment.utils.*
import com.ticketswap.assessment.widgets.DividerItemDecoration
import com.ticketswap.assessment.widgets.SizedDivider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binder: FragmentSearchBinding

    private val navController by lazy { findNavController() }

    private val searchViewModel: SearchViewModel by viewModels()

    private val searchAdapter by lazy { SearchAdapter() }

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
        listenForBackPress(BackPressAction.create { backToExit.invoke() })

        binder.artistTracksList.also {
            it.adapter = searchAdapter
            it.layoutManager = LinearLayoutManager(context)
        }

        searchViewModel.searchResult.observeOnce(viewLifecycleOwner, {
            if (it.action == Action.UNAUTHORISED) {
                navController.navigate(R.id.loginFragment)
            }
            if (it.action == Action.SUCCESS) {
                searchAdapter.setList(it.data!!)
            }
        })

        if (!searchViewModel.isLoggedIn()) {
            navController.navigate(R.id.loginFragment)
        } else {
            searchViewModel.search("hello")
        }
    }
}