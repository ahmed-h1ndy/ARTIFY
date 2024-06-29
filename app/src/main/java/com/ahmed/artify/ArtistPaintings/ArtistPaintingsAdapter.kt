package com.ahmed.artify.ArtistPaintings

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
import com.ahmed.artify.artsy.Artwork.Artwork
import com.bumptech.glide.Glide

public class ArtistPaintingsAdapter(private val paintings: List<Artwork>)
    : RecyclerView.Adapter<ArtistPaintingsAdapter.paintings_view_holder>() {
    inner class paintings_view_holder(view: View): RecyclerView.ViewHolder(view){
        val painting_image: ImageView = view.findViewById(R.id.expand_example)
        val painting_name: TextView = view.findViewById(R.id.expand_painting_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): paintings_view_holder {
        val groupLayout: View = LayoutInflater.from(parent.context).
        inflate(R.layout.style_example,parent,false)
        return paintings_view_holder(groupLayout)
    }

    override fun getItemCount(): Int {
        return paintings.size
    }

    override fun onBindViewHolder(holder: paintings_view_holder, position: Int) {
        val painting = paintings[position]
        holder.painting_name.text = painting.title
        Glide.with(holder.painting_image.context)
            .load(painting._links.thumbnail.href)
            .into(holder.painting_image)
    }

}

