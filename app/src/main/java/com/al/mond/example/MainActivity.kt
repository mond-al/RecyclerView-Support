package com.al.mond.example

import android.os.Bundle
import android.util.Log
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

        // Very simple RecyclerView and Adapter Bind
        findViewById<RecyclerView>(R.id.recyclerview).apply {
            adapter = SimpleAdapter()
            addItemDecoration(SimpleOffsetDecoration(20, 10))
            addOnScrollListener(OnThresholdOverScrollListener { direction ->
                "Direction Changed - $direction".log()
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
                            ("onClickTopArea position ${topViewHolder.adapterPosition} ")
                        }

                    })
            )
        }
    }

    fun String.log() {
        Log.e("ExampleLogger", this)
    }
}