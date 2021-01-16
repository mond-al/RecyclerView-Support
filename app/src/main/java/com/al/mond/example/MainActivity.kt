package com.al.mond.example

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.al.mond.example.simple.SimpleAdapter
import com.al.mond.example.simple.SimpleOffsetDecoration
import com.al.mond.support.dp2px
import com.al.mond.support.recyclerview.OnClickItemTermListener
import com.al.mond.support.recyclerview.OnThresholdOverScrollListener


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val scrollTopButton = findViewById<View>(R.id.btn_scroll_top)

        // Very simple RecyclerView and Adapter Bind
        findViewById<RecyclerView>(R.id.recyclerview).apply {
            adapter = SimpleAdapter()
            addItemDecoration(SimpleOffsetDecoration(20, 10))
            addOnScrollListener(OnThresholdOverScrollListener { direction ->
                "Direction Changed - $direction".log()
                scrollTopButton.visibility = visibleElseGone(direction == OnThresholdOverScrollListener.Direction.Up)
            })
            addOnItemTouchListener(
                OnClickItemTermListener(
                    overlapHeight = 10.dp2px(),
                    listener = object : OnClickItemTermListener.OnClickListener {
                        override fun onClickBetweenArea(
                            rv: RecyclerView,
                            aboveViewHolder: RecyclerView.ViewHolder,
                            belowViewHolder: RecyclerView.ViewHolder
                        ) {
                            ("onClickBetweenArea position ${aboveViewHolder.adapterPosition} ~ position ${belowViewHolder.adapterPosition} ").log()
                        }

                        override fun onClickBottomArea(
                            rv: RecyclerView,
                            bottom: RecyclerView.ViewHolder
                        ) {
                            ("onClickBottomArea position ${bottom.adapterPosition} ").log()
                        }

                        override fun onClickTopArea(
                            rv: RecyclerView,
                            topViewHolder: RecyclerView.ViewHolder
                        ) {
                            ("onClickTopArea position ${topViewHolder.adapterPosition} ").log()
                        }

                    })
            )
        }.also { recyclerView ->
            scrollTopButton.setOnClickListener {
                it.visibility = View.GONE
                recyclerView.scrollToPosition(0)
            }
        }
    }

    private fun visibleElseGone(isVisible: Boolean) =
        if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }

    fun String.log() {
        Log.e("ExampleLogger", this)
    }
}