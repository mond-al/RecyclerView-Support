package com.al.mond.example

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.al.mond.example.simple.SimpleAdapter
import com.al.mond.example.simple.SimpleOffsetDecoration
import com.al.mond.support.recyclerview.OnClickItemTermListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Very simple RecyclerView and Adapter Bind
        findViewById<RecyclerView>(R.id.recyclerview).apply {
            adapter = SimpleAdapter()
            addItemDecoration(SimpleOffsetDecoration(dp2px, dp2px))

            // Add addOnItemTouchListener
            addOnItemTouchListener(OnClickItemTermListener(overlapHeight = 10.dp2px(), listener = object : OnClickItemTermListener.OnClickListener {
                override fun onClickBetweenArea(rv: RecyclerView, aboveViewHolder: RecyclerView.ViewHolder, belowViewHolder: RecyclerView.ViewHolder) {
                    log("onClickBetweenArea position ${aboveViewHolder.adapterPosition} ~ position ${belowViewHolder.adapterPosition} ")
                }

                override fun onClickBottomArea(rv: RecyclerView, bottom: RecyclerView.ViewHolder) {
                    log("onClickBottomArea position ${bottom.adapterPosition} ")
                }

                override fun onClickTopArea(rv: RecyclerView, topViewHolder: RecyclerView.ViewHolder) {
                    log("onClickTopArea position ${topViewHolder.adapterPosition} ")
                }

            }))
        }
    }

    private fun log(str: String) {
        Log.e(MainActivity::javaClass.name, str)
    }
}