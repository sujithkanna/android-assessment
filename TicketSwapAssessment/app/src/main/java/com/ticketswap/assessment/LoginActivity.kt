package com.ticketswap.assessment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var prefs: PrefStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        createUtils()
        setupUI()
    }

    fun createUtils() {
        prefs = PrefStore(this)
    }

    fun setupUI() {
        button_login.setOnClickListener {
            startActivityResult()
        }
    }

    fun startActivityResult() {
        val spotifyClientId =
            "84ea753e599142b8bace9b63d153227b" // Feel free to use this spotify app

        AuthenticationClient.openLoginActivity(
            this, SPOTIFY_LOGIN_REQUEST,
            AuthenticationRequest.Builder(
                spotifyClientId,
                AuthenticationResponse.Type.TOKEN, Uri.Builder()
                    .scheme(getString(R.string.com_spotify_sdk_redirect_scheme))
                    .authority(getString(R.string.com_spotify_sdk_redirect_host))
                    .build().toString()
            )
                .setShowDialog(true)
                .setScopes(arrayOf("user-read-email"))
                .setCampaign("your-campaign-token")
                .build()
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val response = AuthenticationClient.getResponse(resultCode, data)

        if (response.type == AuthenticationResponse.Type.ERROR) {
            Toast.makeText(
                this,
                "Error: ${response.error}",
                Toast.LENGTH_LONG
            )
                .show()
        } else {
            prefs.setAuthToken(response.accessToken)
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    companion object {
        const val SPOTIFY_LOGIN_REQUEST = 101
    }
}
