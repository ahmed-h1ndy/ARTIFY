package com.ahmed.artify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StylesAdapter(private val styles:List<Style>): RecyclerView.Adapter<StylesAdapter.styles_view_holder>() {
    inner class styles_view_holder(view: View): RecyclerView.ViewHolder(view){

        val style_image: ImageView = view.findViewById(R.id.style_image)
        val style_name: TextView = view.findViewById(R.id.style_name)
        val style_example: TextView = view.findViewById(R.id.style_example)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): styles_view_holder {
        val groupLayout: View = LayoutInflater.from(parent.context).
        inflate(R.layout.style_view,parent,false)
        return styles_view_holder(groupLayout)
    }

    override fun getItemCount(): Int {
        return styles.size
    }

    override fun onBindViewHolder(holder: styles_view_holder, position: Int) {
        val style = styles[position]
        holder.style_name.text = style.name
        holder.style_example.text = style.example
        holder.style_image.setImageBitmap(style.image)
    }

}

