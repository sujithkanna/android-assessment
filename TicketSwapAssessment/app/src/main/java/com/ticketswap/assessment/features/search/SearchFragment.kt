package com.ticketswap.assessment.features.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ticketswap.assessment.R
import com.ticketswap.assessment.databinding.FragmentSearchBinding
import com.ticketswap.assessment.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binder: FragmentSearchBinding

    private val navController by lazy { findNavController() }

    private val searchViewModel: SearchViewModel by viewModels()

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
        if (!searchViewModel.isLoggedIn()) {
            navController.navigate(R.id.loginFragment)
        }
        setupObservers()

        listenForBackPress(BackPressAction.create { backToExit.invoke() })

        searchViewModel.search("hello")
    }

    private fun setupObservers() {
        searchViewModel.searchResult.observe(viewLifecycleOwner, Observer {
            Log.i("TestTest", it.action.toString())
        })
    }

}