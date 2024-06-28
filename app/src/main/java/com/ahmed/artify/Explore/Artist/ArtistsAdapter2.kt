package com.ahmed.artify.Explore.Artist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.artify.ExploreExpand.ExploreExpandActivity
import com.ahmed.artify.R
import com.ahmed.artify.artsy.Artists.Artist
import com.bumptech.glide.Glide

public class ArtistsAdapter2(private val artists: List<Artist>,
                             private val context:Context)
    : RecyclerView.Adapter<ArtistsAdapter2.artists_view_holder>() {
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
        Glide.with(holder.artist_image.context)
            .load(artist.artistLinks.thumbnail.href)
            .into(holder.artist_image)

        holder.artist_image.setOnClickListener {
            val intent = Intent(context, ExploreExpandActivity::class.java)
            intent.putExtra("type", artist.id)
            context.startActivity(intent)
        }
    }

}

