package com.example.hadi.newspedia

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log

/**
 * Created by hadi on 01/02/18.
 */
abstract class EndlessRecyclerViewScrollListener : RecyclerView.OnScrollListener{

    private var visibleThreshold: Int = 5
    private var currentPage: Int = 0
    private var prevTotalItemCount: Int = 0
    private var loading: Boolean = true
    private var startingPageIndex: Int = 0

    var mLayoutManager: RecyclerView.LayoutManager

    constructor(layoutManager: LinearLayoutManager) {
        this.mLayoutManager = layoutManager
    }

    constructor(layoutManager: GridLayoutManager) {
        this.mLayoutManager = layoutManager
    }

    constructor(layoutManager: StaggeredGridLayoutManager) {
        this.mLayoutManager = layoutManager
    }

    fun getLastVisibleItem(lastVisibleItemPositions: Array<Int>): Int {
        var maxSize: Int = 0
        for (i in lastVisibleItemPositions.indices){
            if (i == 0)
                maxSize = lastVisibleItemPositions[i]
            else if (lastVisibleItemPositions[i] > maxSize)
                maxSize = lastVisibleItemPositions[i]
        }
        return maxSize
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        val totalItemCount = mLayoutManager.itemCount

        val lastVisibleItemPosition = when (mLayoutManager){
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions = (mLayoutManager as StaggeredGridLayoutManager)
                        .findLastVisibleItemPositions(null)
                getLastVisibleItem(lastVisibleItemPositions.toTypedArray())
            }
            is GridLayoutManager -> (mLayoutManager as GridLayoutManager)
                    .findLastVisibleItemPosition()
            is LinearLayoutManager -> (mLayoutManager as LinearLayoutManager)
                    .findLastVisibleItemPosition()
            else -> 0
        }

        if (totalItemCount < prevTotalItemCount) {
            this.currentPage = startingPageIndex
            this.prevTotalItemCount = totalItemCount
            if (totalItemCount == 0) this.loading = true
        }

        if (loading && (totalItemCount > prevTotalItemCount)){
            loading = false
            prevTotalItemCount = totalItemCount
        }

        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount){
            currentPage++
            onLoadMore(currentPage, totalItemCount, recyclerView)
            this.loading = true
        }
    }

    fun resetState() {
        this.currentPage = this.startingPageIndex
        this.prevTotalItemCount = 0
        this.loading = true
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?)


}