package com.ticketswap.assessment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View.GONE
import android.view.View.VISIBLE
import com.ticketswap.assessment.spotify.SpotifyApi
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    lateinit var prefs: PrefStore

    @Inject
    lateinit var retrofit: Retrofit

    lateinit var spotifyApi: SpotifyApi

    lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createUtils()
        setupUI()

        showLogin()
    }

    // Setup

    fun createUtils() {
        spotifyApi = retrofit.create(SpotifyApi::class.java)
        prefs = PrefStore(this)
        adapter = RecyclerAdapter()
    }

    fun setupUI() {
        hideEverything()

        button_login.setOnClickListener {
            startActivityResult()
        }

        button_search.setOnClickListener {
            search()
        }

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
    }

    // Navigation

    fun hideEverything() {
        button_login.visibility = GONE
        button_search.visibility = GONE
        search_edit_text.visibility = GONE
        recycler.visibility = GONE
    }

    fun showLogin() {
        hideEverything()
        button_login.visibility = VISIBLE
    }

    fun showSearch() {
        hideEverything()
        button_search.visibility = VISIBLE
        search_edit_text.visibility = VISIBLE
        recycler.visibility = VISIBLE
    }

    // Login

    fun startActivityResult() {
        val spotifyClientId = "84ea753e599142b8bace9b63d153227b" // Feel free to use this spotify app

        AuthenticationClient.openLoginActivity(this, 137,
                AuthenticationRequest.Builder(
                        spotifyClientId,
                        AuthenticationResponse.Type.TOKEN, Uri.Builder()
                        .scheme(getString(R.string.com_spotify_sdk_redirect_scheme))
                        .authority(getString(R.string.com_spotify_sdk_redirect_host))
                        .build().toString())
                        .setShowDialog(true)
                        .setScopes(arrayOf("user-read-email"))
                        .setCampaign("your-campaign-token")
                        .build()
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val response = AuthenticationClient.getResponse(resultCode, data)
        prefs.setAuthToken(response.accessToken)
        showSearch()
    }

    // Search

    fun search() {
        // Get the artists and tracks
        spotifyApi
                .searchSpotify(search_edit_text.text.toString(), "track,artist")
                .subscribeOn(Schedulers.io())
                .subscribe { result ->
                    // Combine the artists and tracks
                    val arrayList = ArrayList<String>()
                    arrayList.addAll(result.artists.items.map { "Artist: ${it.name}" })
                    arrayList.addAll(result.tracks.items.map { "Track: ${it.name}" })

                    // Update the screen
                    runOnUiThread {
                        adapter.items = arrayList
                        adapter.notifyDataSetChanged()
                    }
                }

    }
}
