package com.ahmed.artify.Game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.ahmed.artify.Game.Artist.game_artist
import com.ahmed.artify.R

class game_main : AppCompatActivity() {

    lateinit var artist_quiz:Button
    lateinit var style_quiz:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_main)

        artist_quiz = findViewById(R.id.main_game_artist_button)
        style_quiz = findViewById(R.id.main_game_style_button)

        artist_quiz.setOnClickListener {
            val intent = Intent(applicationContext, game_artist::class.java)
            startActivity(intent)
        }
    }
}