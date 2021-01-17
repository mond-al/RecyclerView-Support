package com.al.mond.support

import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import java.util.*


// About Display Numbers

fun Number.dp2px() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    Resources.getSystem().displayMetrics
).toInt()

// About Date

fun Long.toDate() = Date(this)
fun Date.fromDate() = time

// About Point

fun View.getViewRawY(): Float {
    val location = IntArray(2)
    location[0] = 0
    location[1] = y.toInt()
    (parent as View).getLocationInWindow(location)
    return location[1].toFloat()
}

fun Number.getInMinMaX(min: Number, max: Number): Float {
    val minimum = min.toFloat().coerceAtLeast(this.toFloat())
    return minimum.coerceAtMost(max.toFloat())
}
