package com.ahmed.artify.Explore.Artist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.artify.Helpers.Artist
import com.ahmed.artify.R
import com.ahmed.artify.RetrofitClass.ApiRequests
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class artist_fragment : Fragment(R.layout.fragment_artist) {

    lateinit var artist_recycler: RecyclerView
    var artists: ArrayList<Artist> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val api = ApiRequests()
        artist_recycler = view.findViewById(R.id.artist_recycler)

        if (artists.size!=0) {
            artist_recycler.adapter = ArtistsAdapter(artists, requireContext())
            artist_recycler.layoutManager = GridLayoutManager(context, 3)
        } else {
            GlobalScope.launch {
                artists = api.initialize_artists {  }
                artist_recycler.adapter = ArtistsAdapter(artists, requireContext())
                artist_recycler.layoutManager = GridLayoutManager(context, 3)
            }

        }



    }


}