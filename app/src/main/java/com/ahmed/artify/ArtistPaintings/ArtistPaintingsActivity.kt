package com.ahmed.artify.ArtistPaintings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmed.artify.Explore.Artist.ArtistsAdapter2
import com.ahmed.artify.Explore.ExploreActivity
import com.ahmed.artify.R
import com.ahmed.artify.artsy.ArtsyApiObject
import com.ahmed.artify.artsy.Artwork.Artwork
import com.ahmed.artify.databinding.ActivityArtistPaintingsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistPaintingsActivity : AppCompatActivity() {

    lateinit var paintings: List<Artwork>
    lateinit var binding: ActivityArtistPaintingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistPaintingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id")
        MainScope().launch {
            val res = ArtsyApiObject.retrofitService.getArtworks(10, id!!)

            if(res.isSuccessful){
                paintings = res.body()!!._embedded.artworks

                withContext(Dispatchers.Main) {
                    binding.paintingsRecycler.visibility = View.VISIBLE
                    binding.paintingsLoading.visibility = View.GONE
                    binding.paintingsRecycler.adapter = ArtistPaintingsAdapter(paintings)
                    binding.paintingsRecycler.layoutManager = GridLayoutManager(applicationContext, 2)

                }

            }
        }

        binding.paintingsBackArrow.setOnClickListener {
            val intent = Intent(applicationContext, ExploreActivity::class.java)
            startActivity(intent)
        }

    }
}