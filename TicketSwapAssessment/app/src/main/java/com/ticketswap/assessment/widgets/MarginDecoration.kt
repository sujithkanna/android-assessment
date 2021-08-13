package com.ticketswap.assessment.widgets

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class MarginDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.bottom = margin / 2
        outRect.top = margin / 2

    }
}