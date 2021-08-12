package com.ticketswap.assessment.utils


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kotlin.math.max

private abstract class BaseLoadMore constructor(
    open val latch: LoaderLatch,
    open val layoutManager: LayoutManager,
    open val loadMore: () -> Unit
) : RecyclerView.OnScrollListener() {

}

const val LOAD_ITEM_DEFAULT_THRESHOLD = 8


private open class LoadMoreOnScroll(
    override val latch: LoaderLatch,
    val count: Int,
    override val layoutManager: LinearLayoutManager,
    loadMore: () -> Unit
) : BaseLoadMore(latch, layoutManager, loadMore) {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (latch.canLoadMore()) {
            val start = (max(0, layoutManager.itemCount - count))
            val range = start until layoutManager.itemCount
            if (layoutManager.findLastVisibleItemPosition() in range) loadMore()
        }
    }
}


fun RecyclerView.loadIfLessThan(
    latch: LoaderLatch,
    count: Int = LOAD_ITEM_DEFAULT_THRESHOLD,
    loadMore: () -> Unit
) {
    val layoutManager = layoutManager
    if (layoutManager is LinearLayoutManager) {
        addOnScrollListener(LoadMoreOnScroll(latch, count, layoutManager, loadMore))
    }
}

interface LoaderLatch {
    fun canLoadMore(): Boolean
}