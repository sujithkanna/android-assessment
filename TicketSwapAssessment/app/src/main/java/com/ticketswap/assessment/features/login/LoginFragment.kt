package com.ticketswap.assessment.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.ticketswap.assessment.R
import com.ticketswap.assessment.databinding.FragmentLoginBinding
import com.ticketswap.assessment.utils.observeNotNull
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binder: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

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
        loginViewModel.spotifyAuthRequest.observeNotNull(this, {
            val intent = AuthenticationClient.createLoginActivityIntent(activity, it)
            loginResultLauncher.launch(intent)
        })

        loginViewModel.loginResult.observeNotNull(this, { success ->
            if (success) {
                showRetry()
            } else showRetry()
        })

        binder.buttonLogin.setOnClickListener {
            loginViewModel.doLogin()
        }
    }

    private fun showRetry() {
        Snackbar.make(binder.root, R.string.spotify_login_failed, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.try_again) {
                loginViewModel.doLogin()
            }.show()
    }
}
