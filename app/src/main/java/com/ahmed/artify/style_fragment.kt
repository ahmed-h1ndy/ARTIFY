package com.ahmed.artify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class style_fragment : Fragment(R.layout.fragment_style) {

    lateinit var style_recycler: RecyclerView
    lateinit var styles: ArrayList<Style>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        style_recycler = view.findViewById(R.id.style_recycler)
        styles = ArrayList()

        style_recycler.adapter = styles_adapter(fetch_data())
        style_recycler.layoutManager = GridLayoutManager(context,2)
    }

    private fun fetch_data(): List<Style> {
        styles.add(Style("Japanese Art","something","none"))
        styles.add(Style("Japanese Art","something","none"))
        styles.add(Style("Japanese Art","something","none"))
        styles.add(Style("Japanese Art","something","none"))
        return styles;
    }
}