package com.ticketswap.assessment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ticketswap.assessment.spotify.SpotifyApi
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Retrofit
import javax.inject.Inject

class SearchActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var retrofit: Retrofit

    lateinit var spotifyApi: SpotifyApi

    lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        createUtils()
        setupUI()
    }

    // Setup

    fun createUtils() {
        spotifyApi = retrofit.create(SpotifyApi::class.java)
        adapter = RecyclerAdapter()
    }

    fun setupUI() {
        button_search.setOnClickListener {
            search()
        }

        recycler.adapter = adapter
        recycler.layoutManager =
            LinearLayoutManager(this)
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
