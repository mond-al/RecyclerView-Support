package com.al.mond.support.recyclerview

import android.view.MotionEvent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * use for only LinearLayoutManager
 */
class OnClickItemTermListener(
    private val overlapHeight: Int = 0,
    private val listener: OnClickListener
) :
    RecyclerView.SimpleOnItemTouchListener() {
    private val mDistanceLimitOnClick: Float = 10f
    private fun getOverlapHeight(itemHeight: Int) = when {
        itemHeight / 2 < overlapHeight -> 0
        else -> overlapHeight
    }

    private var mIsClicked: Boolean = false
    private var mDownX: Float = 0.toFloat()
    private var mDownY: Float = 0.toFloat()
    override fun onInterceptTouchEvent(rv: RecyclerView, event: MotionEvent): Boolean {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                mDownX = event.x
                mDownY = event.y
                mIsClicked = true
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> if (mIsClicked) {
                mIsClicked = false

                // ignore cases
                val layoutManager = (rv.layoutManager as? LinearLayoutManager) ?: return false
                if (layoutManager is GridLayoutManager && layoutManager.spanCount > 1) return false
                val adapter = rv.adapter ?: return false


                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                // Selection Top
                if (firstVisibleItemPosition == 0) {
                    val firstViewHolder =
                        rv.findViewHolderForAdapterPosition(firstVisibleItemPosition)
                    val firstHeight = firstViewHolder?.itemView?.height
                    val firstViewHolderTop = firstViewHolder?.itemView?.top
                    if (firstHeight != null && firstViewHolderTop != null) {
                        val endY = firstViewHolderTop + getOverlapHeight(firstHeight)
                        if (0 < event.y && event.y < endY) {
                            listener.onClickTopArea(rv, firstViewHolder)
                            return false
                        }
                    }
                }

                // Selection Bottom
                if (lastVisibleItemPosition == adapter.itemCount - 1) {
                    val lastItemViewHolder =
                        rv.findViewHolderForAdapterPosition(lastVisibleItemPosition)
                    val lastItemViewHoldersBottom = lastItemViewHolder?.itemView?.bottom

                    if (lastItemViewHoldersBottom != null && lastItemViewHoldersBottom < event.y) {
                        listener.onClickBottomArea(rv, lastItemViewHolder)
                        return false
                    }
                }

                // Selection Gap
                for (position in firstVisibleItemPosition until lastVisibleItemPosition) {
                    val aboveViewHolder = rv.findViewHolderForAdapterPosition(position)
                    val aboveViewHolderHeight = aboveViewHolder?.itemView?.height
                    val aboveViewHolderBottomY = aboveViewHolder?.itemView?.bottom

                    val belowViewHolder = rv.findViewHolderForAdapterPosition(position + 1)
                    val belowViewHolderHeight = belowViewHolder?.itemView?.height
                    val belowViewHolderTopY = belowViewHolder?.itemView?.top

                    if (aboveViewHolderBottomY != null && aboveViewHolderHeight != null
                        && belowViewHolderTopY != null && belowViewHolderHeight != null
                    ) {
                        val startY =
                            aboveViewHolderBottomY - getOverlapHeight(aboveViewHolderHeight)
                        val endY = belowViewHolderTopY + getOverlapHeight(belowViewHolderHeight)
                        if (startY < event.y && event.y < endY) {
                            listener.onClickBetweenArea(rv, aboveViewHolder, belowViewHolder)
                            return false
                        }
                    }
                }

            }
            MotionEvent.ACTION_MOVE -> {
                if (mIsClicked && (Math.abs(mDownX - event.x) > mDistanceLimitOnClick ||
                            Math.abs(mDownY - event.y) > mDistanceLimitOnClick)
                ) {
                    mIsClicked = false
                }

            }
        }
        return false
    }

    interface OnClickListener {
        fun onClickTopArea(
            rv: RecyclerView,
            topViewHolder: RecyclerView.ViewHolder
        )

        fun onClickBottomArea(
            rv: RecyclerView,
            bottom: RecyclerView.ViewHolder
        )

        fun onClickBetweenArea(
            rv: RecyclerView,
            aboveViewHolder: RecyclerView.ViewHolder,
            belowViewHolder: RecyclerView.ViewHolder
        )
    }
}