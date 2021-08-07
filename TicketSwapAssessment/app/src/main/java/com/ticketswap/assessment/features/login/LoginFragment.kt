package com.ticketswap.assessment.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.ticketswap.assessment.R
import com.ticketswap.assessment.databinding.FragmentLoginBinding
import com.ticketswap.assessment.utils.BackPress
import com.ticketswap.assessment.utils.observeNotNull
import com.ticketswap.assessment.utils.pressBackToExit
import com.ticketswap.assessment.utils.showToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binder: FragmentLoginBinding

    private val loginViewModel: LoginViewModel by viewModels()

    private val navController by lazy { findNavController() }

    private val backPressCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backToExit.invoke()
            }
        }
    }

    private val backToExit by lazy {
        pressBackToExit(lifecycleScope) {
            when (it) {
                BackPress.LAST -> requireActivity().finish()
                BackPress.FIRST -> showToast(R.string.press_again_to_exit, Toast.LENGTH_LONG)
            }
        }
    }

    private val loginResultLauncher = registerForActivityResult(StartActivityForResult()) {
        val response = AuthenticationClient.getResponse(it.resultCode, it.data)
        loginViewModel.processLoginResponse(response)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = FragmentLoginBinding.inflate(inflater, container, false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressCallback)

        loginViewModel.spotifyAuthRequest.observeNotNull(this, {
            val intent = AuthenticationClient.createLoginActivityIntent(activity, it)
            loginResultLauncher.launch(intent)
        })

        loginViewModel.loginResult.observeNotNull(this, { success ->
            if (success) navController.popBackStack() else showRetry()
        })

        binder.buttonLogin.setOnClickListener { loginViewModel.doLogin() }
    }

    private fun showRetry() {
        Snackbar.make(binder.root, R.string.spotify_login_failed, Snackbar.LENGTH_LONG)
            .setAction(R.string.try_again) { loginViewModel.doLogin() }.show()
    }
}
