package com.ticketswap.assessment.widgets

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class SizedDivider(val context: Context, @RecyclerView.Orientation val orientation: Int) :
    RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }
}