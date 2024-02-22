package com.ahmed.artify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.artify.explore.ApiRequests


class artist_fragment : Fragment(R.layout.fragment_artist) {

    lateinit var artist_recycler: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val api = ApiRequests()
        artist_recycler = view.findViewById(R.id.artist_recycler)
        api.getArtists(artist_recycler, context)


    }


}