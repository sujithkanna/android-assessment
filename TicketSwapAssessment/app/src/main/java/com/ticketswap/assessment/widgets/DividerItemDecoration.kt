package com.ticketswap.assessment.widgets

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import kotlin.math.roundToInt

// https://gist.github.com/alexfu/0f464fc3742f134ccd1e
class DividerItemDecoration(context: Context, orientation: Int, private val padding: Float = 0f) :
    ItemDecoration() {
    private val mDivider: Drawable
    private var mOrientation = 0
    private fun setOrientation(orientation: Int) {
        require(!(orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST)) { "invalid orientation" }
        mOrientation = orientation
    }

    override fun onDraw(c: Canvas, parent: RecyclerView) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft + padding
        val right = parent.width - parent.paddingRight - padding
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val top: Int = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight
            mDivider.setBounds(left.roundToInt(), top, right.roundToInt(), bottom)
            mDivider.draw(c)
        }
    }

    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val left: Int = child.right + params.rightMargin
            val right = left + mDivider.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.intrinsicHeight)
        } else {
            outRect.set(0, 0, mDivider.intrinsicWidth, 0)
        }
    }

    companion object {
        private val ATTRS = intArrayOf(
            R.attr.listDivider
        )
        const val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL
        const val VERTICAL_LIST = LinearLayoutManager.VERTICAL
    }

    init {
        val a: TypedArray = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        a.recycle()
        setOrientation(orientation)
    }
}