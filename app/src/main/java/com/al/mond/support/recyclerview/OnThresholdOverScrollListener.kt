package com.al.mond.support.recyclerview

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class OnThresholdOverScrollListener(
    private val threshold: Int = Resources.getSystem().displayMetrics.heightPixels / 10, // 10% of screen height
    private val callback: (direction: Direction) -> Unit
) : RecyclerView.OnScrollListener() {
    private var oldDirection = Direction.None
    private var sameDirectionDistanceY = 0
    private var thresholdOver = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val direction = Direction.by(dy)
        if (oldDirection != direction) {
            init(direction)
        } else if (oldDirection == direction) {
            // sameDirectionDistanceY is add only in the same direction.
            sameDirectionDistanceY += dy

            if (threshold < abs(sameDirectionDistanceY) && thresholdOver.not()) {
                callback.invoke(oldDirection)
                thresholdOver = true
            }
        }
    }

    fun init(direction: Direction) {
        oldDirection = direction
        sameDirectionDistanceY = 0
        thresholdOver = false
    }

    enum class Direction {
        Up, Down, None;

        override fun toString() =
            when (this) {
                Up -> "UP"
                Down -> "DOWN"
                None -> "NONE"
            }

        companion object {
            internal fun by(dy: Int) =
                when {
                    dy > 0 -> Down
                    dy < 0 -> Up
                    else -> None
                }
        }
    }
}