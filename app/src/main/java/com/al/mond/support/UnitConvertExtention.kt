package com.al.mond.support.recyclerview

import android.content.res.Resources
import android.util.TypedValue
import java.util.*

/**
 * This file will be finally separated.
 */

// About Display Numbers

fun Number.dp2px() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    Resources.getSystem().displayMetrics
).toInt()

// About Date

fun Long.toDate() = Date(this)
fun Date.fromDate() = time