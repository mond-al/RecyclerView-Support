package com.al.mond.example

import android.content.res.Resources
import android.util.TypedValue

internal fun Number.dp2px(): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()
}