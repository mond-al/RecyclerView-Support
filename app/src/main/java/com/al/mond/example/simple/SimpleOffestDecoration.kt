package com.al.mond.example.simple

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.al.mond.example.dp2px

class SimpleOffestDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent,state)
        outRect.top = 30.dp2px()
        outRect.bottom = 30.dp2px()
    }
}