package com.ahmed.artify

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class artist_fragment : Fragment(R.layout.fragment_artist) {

    lateinit var artists: ArrayList<Artist>

    lateinit var artist_recycler: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        artist_recycler = view.findViewById(R.id.artist_recycler)
        artists = ArrayList()

        artist_recycler.adapter = artists_adapter(fetch_data())
        artist_recycler.layoutManager = GridLayoutManager(context,3)
    }

    private fun fetch_data(): List<Artist> {
        artists.add(Artist("Da Vinci","none"))
        artists.add(Artist("Picasso","none"))
        artists.add(Artist("Van Gogh","none"))
        artists.add(Artist("Da Vinci","none"))
        artists.add(Artist("Picasso","none"))
        artists.add(Artist("Van Gogh","none"))
        artists.add(Artist("Da Vinci","none"))
        artists.add(Artist("Picasso","none"))
        artists.add(Artist("Van Gogh","none"))

        return artists
    }

}