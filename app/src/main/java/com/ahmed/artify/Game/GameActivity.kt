package com.ahmed.artify.Game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahmed.artify.Game.Artist.GameArtist
import com.ahmed.artify.Game.Style.GameStyle
import com.ahmed.artify.databinding.ActivityGameMainBinding

class game_main : AppCompatActivity() {

    lateinit var binding : ActivityGameMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.mainGameArtistButton.setOnClickListener {
            val intent = Intent(applicationContext, GameArtist::class.java)
            startActivity(intent)
        }

        binding.mainGameStyleButton.setOnClickListener {
            val intent = Intent(applicationContext, GameStyle::class.java)
            startActivity(intent)
        }
    }
}