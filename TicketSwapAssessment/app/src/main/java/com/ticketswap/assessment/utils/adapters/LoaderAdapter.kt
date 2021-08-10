package com.ticketswap.assessment.utils.adapters


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class LoaderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var loaderCount = 1
    private var showLoading = false
    override fun getItemCount(): Int {
        return getData().size + if (showLoading) loaderCount else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createLoader(parent)
    }

    abstract fun getData(): List<Any>

    override fun getItemViewType(position: Int): Int {
        return if (showLoading && (position >= itemCount - loaderCount)) TYPE_LOADING else TYPE_CARD
    }

    fun showLoading(show: Boolean) {
        if (showLoading != show) {
            val count = itemCount
            showLoading = show
            if (show) {
                notifyItemRangeInserted(count, loaderCount)
            } else {
                notifyItemRangeRemoved(count, loaderCount)
            }
        }
    }

    fun setLoaderCount(count: Int) {
        if (loaderCount != count) {
            val loading = showLoading
            if (loading) {
                showLoading(false)
            }
            loaderCount = count
            if (loading) {
                showLoading(loading)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LoaderViewHolder) {
            (if (showLoading) View.VISIBLE else View.GONE).let { holder.itemView.visibility = it }
        }
    }

    protected abstract fun createLoader(parent: ViewGroup): RecyclerView.ViewHolder

    class LoaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        const val TYPE_CARD = 1
        const val TYPE_LOADING = 0
        const val TYPE_SHOW_BLOCKED_USER = 2
    }
}