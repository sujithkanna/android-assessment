package com.ticketswap.assessment.utils


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import kotlin.math.max

private abstract class BaseLoadMore constructor(
    open val layoutManager: LayoutManager,
    open val loadMore: () -> Unit
) : RecyclerView.OnScrollListener() {

}

const val LOAD_ITEM_DEFAULT_THRESHOLD = 8


private open class LoadMoreOnScroll(
    val count: Int,
    override val layoutManager: LinearLayoutManager,
    loadMore: () -> Unit
) : BaseLoadMore(layoutManager, loadMore) {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == SCROLL_STATE_IDLE) {
            val start = (max(0, layoutManager.itemCount - count))
            val range = start until layoutManager.itemCount
            if (layoutManager.findLastVisibleItemPosition() in range) {
                loadMore()
            }
        }
    }
}


fun RecyclerView.loadIfLessThan(count: Int = LOAD_ITEM_DEFAULT_THRESHOLD, loadMore: () -> Unit) {
    val layoutManager = layoutManager
    if (layoutManager is LinearLayoutManager) {
        addOnScrollListener(LoadMoreOnScroll(count, layoutManager, loadMore))
    }
}