package com.ticketswap.assessment.utils

import androidx.recyclerview.widget.RecyclerView.*

class DataChangeListener private constructor(
    private val adapter: Adapter<ViewHolder>, private val callback: () -> Unit
) : AdapterDataObserver() {

    init {
        adapter.registerAdapterDataObserver(this)
    }

    override fun onChanged() {
        callback()
        adapter.unregisterAdapterDataObserver(this)
    }

    companion object {
        fun register(adapter: Adapter<ViewHolder>, callback: () -> Unit) =
            DataChangeListener(adapter, callback)
    }
}