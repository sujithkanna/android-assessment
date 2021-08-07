package com.ticketswap.assessment.features.login

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.ticketswap.assessment.R
import com.ticketswap.assessment.databinding.ActivityLoginBinding
import com.ticketswap.assessment.utils.observeNotNull
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binder: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    private val loginResultLauncher = registerForActivityResult(StartActivityForResult()) {
        val response = AuthenticationClient.getResponse(it.resultCode, it.data)
        loginViewModel.processLoginResponse(response)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binder.root)

        loginViewModel.spotifyAuthRequest.observeNotNull(this, {
            val intent = AuthenticationClient.createLoginActivityIntent(this, it)
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
