package com.ahmed.artify.Explore

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.FragmentContainerView
import com.ahmed.artify.Explore.Artist.artist_fragment
import com.ahmed.artify.Explore.Style.style_fragment
import com.ahmed.artify.R

class ExploreActivity : AppCompatActivity() {
    lateinit var artists_button: Button
    lateinit var styles_button: Button
    lateinit var explore_fragment: FragmentContainerView
    lateinit var nav_bar: LinearLayout

    lateinit var selected_button: Button
    lateinit var not_selected: Button
    lateinit var ta: TranslateAnimation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)

        artists_button = findViewById(R.id.explore_artists_button)
        styles_button = findViewById(R.id.explore_styles_button)
        explore_fragment = findViewById(R.id.explore_fragment)
        selected_button = artists_button
        not_selected = styles_button
        selected_button.setBackgroundResource(R.drawable.curved_white)
        selected_button.setTextColor(Color.BLACK)
        ta = TranslateAnimation(0-1*selected_button.width.toFloat(),0F , 0F,0F)


        artists_button.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {

                val currentFragment = supportFragmentManager.findFragmentById(R.id.explore_fragment)
                if (currentFragment != null) {
                    remove(currentFragment)
                }

                replace(R.id.explore_fragment, artist_fragment())
                commit()
            }

            selected_button = artists_button
            not_selected = styles_button
            ta = TranslateAnimation(0-1*selected_button.width.toFloat(),0F , 0F,0F)
            ta.duration = 100
            ta.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    selected_button.setBackgroundResource(R.drawable.curved_white)
                    selected_button.setTextColor(Color.BLACK)
                }

                override fun onAnimationEnd(animation: Animation) {
                    not_selected.setBackgroundResource(R.drawable.button_primary)
                    not_selected.setTextColor(Color.WHITE)


                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            })
            selected_button.startAnimation(ta)
        }

        styles_button.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {

                val currentFragment = supportFragmentManager.findFragmentById(R.id.explore_fragment)
                if (currentFragment != null) {
                    remove(currentFragment)
                }

                replace(R.id.explore_fragment, style_fragment())
                commit()

                selected_button = styles_button
                not_selected = artists_button
                ta = TranslateAnimation(selected_button.width.toFloat(),0F , 0F,0F)
                ta.duration = 100
                ta.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {
                        selected_button.setBackgroundResource(R.drawable.curved_white)
                        selected_button.setTextColor(Color.BLACK)
                    }

                    override fun onAnimationEnd(animation: Animation) {
                        not_selected.setBackgroundResource(R.drawable.button_primary)
                        not_selected.setTextColor(Color.WHITE)



                    }

                    override fun onAnimationRepeat(animation: Animation) {

                    }
                })
                selected_button.startAnimation(ta)
            }
        }


    }

}

