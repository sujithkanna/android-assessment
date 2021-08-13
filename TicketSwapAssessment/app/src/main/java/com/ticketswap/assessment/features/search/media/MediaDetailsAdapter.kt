package com.ticketswap.assessment.features.search.media

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ticketswap.assessment.databinding.InflaterMediaDetailsItemBinding

class MediaDetailsAdapter(private val details: List<Media>) :
    RecyclerView.Adapter<MediaDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaDetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MediaDetailsViewHolder(
            InflaterMediaDetailsItemBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MediaDetailsViewHolder, position: Int) {
        val data = details[position]
        holder.bind(data)
    }

    override fun getItemCount() = details.size
}

data class Media(val title: String, val details: String)

class MediaDetailsViewHolder(private val binder: InflaterMediaDetailsItemBinding) :
    RecyclerView.ViewHolder(binder.root) {
    fun bind(data: Media) {
        binder.title.text = data.title
        binder.details.text = data.details
    }

}