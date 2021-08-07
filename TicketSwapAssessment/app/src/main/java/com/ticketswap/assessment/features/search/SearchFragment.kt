package com.ticketswap.assessment.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ticketswap.assessment.R
import com.ticketswap.assessment.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binder: FragmentSearchBinding

    private val navController by lazy { findNavController() }

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedState: Bundle?
    ): View {
        binder = FragmentSearchBinding.inflate(inflater, container, false)
        return binder.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!searchViewModel.isLoggedIn()) {
            navController.navigate(R.id.loginFragment)
        }
    }

}