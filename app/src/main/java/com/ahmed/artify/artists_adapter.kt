package com.ahmed.artify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class artists_adapter(private val artists:List<Artist>): RecyclerView.Adapter<artists_adapter.artists_view_holder>() {
    inner class artists_view_holder(view: View): RecyclerView.ViewHolder(view){

        val artist_image: ImageView = view.findViewById(R.id.artist_image)
        val artist_name: TextView = view.findViewById(R.id.artist_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): artists_view_holder {
        val groupLayout: View = LayoutInflater.from(parent.context).
        inflate(R.layout.artist_view,parent,false)
        return artists_view_holder(groupLayout)
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    override fun onBindViewHolder(holder: artists_view_holder, position: Int) {
        val artist = artists[position]
        holder.artist_name.text = artist.name

    }

}

