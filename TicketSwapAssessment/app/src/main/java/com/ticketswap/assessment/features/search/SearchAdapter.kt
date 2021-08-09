package com.ticketswap.assessment.features.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ticketswap.assessment.R
import com.ticketswap.assessment.databinding.InflaterSearchItemBinding
import com.ticketswap.assessment.models.Item
import com.ticketswap.assessment.utils.*

class SearchAdapter : RecyclerView.Adapter<MediaViewHolder>() {

    private val diffUtil by lazy { createDiff(this) }

    override fun getItemViewType(position: Int) = resolveItemType(diffUtil.currentList[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_ARTIST -> ArtistViewHolder(
                InflaterSearchItemBinding.inflate(inflater, parent, false)
            )
            TYPE_TRACK -> TrackViewHolder(
                InflaterSearchItemBinding.inflate(inflater, parent, false)
            )
            else -> ArtistViewHolder(
                InflaterSearchItemBinding.inflate(inflater, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val data = diffUtil.currentList[position]
        holder.bind(data)
    }

    override fun getItemCount() = diffUtil.currentList.size

    fun setList(list: List<Item>, callback: Runnable? = null) {
        diffUtil.submitList(list, callback)
    }

    companion object {
        const val TYPE_TRACK = 0
        const val TYPE_ARTIST = 1
    }
}

class TrackViewHolder(override val binder: InflaterSearchItemBinding) : MediaViewHolder(binder) {
    override fun bind(item: Item) {
        super.bind(item)
        binder.details.text = getAlbumOrArtist(item)
        val duration = item.duration_ms.toTimerString()
        if (duration != null) binder.duration.text = "($duration)"
    }

    private fun getAlbumOrArtist(item: Item): String {
        return item.album?.name ?: item.artists?.map { it.items?.map { item -> item.name } }
            ?.joinToString(",") ?: ""
    }
}

class ArtistViewHolder(override val binder: InflaterSearchItemBinding) : MediaViewHolder(binder) {
    override fun bind(item: Item) {
        super.bind(item)
        binder.details.text = "${prettyCount(item.followers?.total?.toLong() ?: 0L)} Followers"
    }
}

abstract class MediaViewHolder(protected open val binder: InflaterSearchItemBinding) :
    RecyclerView.ViewHolder(binder.root) {
    open fun bind(item: Item) {
        binder.title.text = item.name
        val image = resolveImageForItem(item)
        if (image == null) {
            binder.icon.setImageResource(R.drawable.ic_music)
        } else {
            Picasso.with(binder.root.context).load(image).transform(CircleTransform())
                .into(binder.icon)
        }

        binder.type.text = item.type.firstCaps()
        val color = resolveColorForType(itemView.context, item.type)

        binder.type.setBackgroundDrawableColor(color)
    }
}

private fun resolveImageForItem(item: Item): String? {
    return (item.images ?: item.album?.images)?.firstOrNull()?.url
}

@ColorInt
private fun resolveColorForType(context: Context, type: String): Int {
    @ColorRes val resId = when (type) {
        "artist" -> R.color.burnt_sienna
        "track" -> R.color.lochmara
        else -> R.color.colorPrimaryDark
    }
    return ContextCompat.getColor(context, resId)
}

private fun resolveItemType(item: Item): Int {
    return when (item.type) {
        "album" -> SearchAdapter.TYPE_ARTIST
        "track" -> SearchAdapter.TYPE_TRACK
        else -> SearchAdapter.TYPE_ARTIST
    }
}

fun <T : RecyclerView.ViewHolder> createDiff(adapter: RecyclerView.Adapter<T>)
        : AsyncListDiffer<Item> {
    return AsyncListDiffer(adapter, object : DiffUtil.ItemCallback<Item>() {

        override fun areItemsTheSame(
            oldItem: Item,
            newItem: Item
        ): Boolean {
            return (oldItem.id == newItem.id)
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.type == newItem.type
                    && oldItem.name == newItem.name
        }
    })
}