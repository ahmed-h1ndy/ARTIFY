package com.ahmed.artify.Explore.Artist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.artify.R
import com.ahmed.artify.RetrofitClass.ApiRequests


class artist_fragment : Fragment(R.layout.fragment_artist) {

    lateinit var artist_recycler: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val api = ApiRequests()
        artist_recycler = view.findViewById(R.id.artist_recycler)

        if (api.artists != null) {
            artist_recycler.adapter = ArtistsAdapter(api.artists, requireContext())
            artist_recycler.layoutManager = GridLayoutManager(context, 3)
        } else {
            api.initialize_artists {artists ->
                artist_recycler.adapter = ArtistsAdapter(artists, requireContext())
                artist_recycler.layoutManager = GridLayoutManager(context, 3)
            }
        }



    }


}