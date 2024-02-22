package com.ahmed.artify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.artify.explore.ApiRequests


class style_fragment : Fragment(R.layout.fragment_style) {

    lateinit var style_recycler: RecyclerView
    lateinit var styles: ArrayList<Style>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        style_recycler = view.findViewById(R.id.style_recycler)
        val api = ApiRequests()
        api.getAllArtStyles(style_recycler, context)
    }

}