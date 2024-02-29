package com.ahmed.artify

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [bottom_nav_bar.newInstance] factory method to
 * create an instance of this fragment.
 */
class bottom_nav_bar : Fragment() {

    lateinit var classify:LinearLayout
    lateinit var quiz_image:ImageView
    lateinit var quiz_text:TextView
    lateinit var explore_image:ImageView
    lateinit var explore_text:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_nav_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        classify = view.findViewById(R.id.nav_classification_button)
        quiz_image = view.findViewById(R.id.nav_quiz_button)
        quiz_text = view.findViewById(R.id.nav_quiz_text)
        explore_image = view.findViewById(R.id.nav_explore_button)
        explore_text = view.findViewById(R.id.nav_explore_text)

        classify.setOnClickListener{
            goToClassification()
        }
        quiz_image.setOnClickListener{
            goToQuiz()
        }
        quiz_text.setOnClickListener{
            goToQuiz()
        }
        explore_text.setOnClickListener{
            goToExplore()
        }
        explore_image.setOnClickListener{
            goToExplore()
        }


    }

    fun goToExplore(){
        val intent = Intent(context, ExploreActivity::class.java)
        startActivity(intent)
    }
    fun goToQuiz(){
        val intent = Intent(context, game_main::class.java)
        startActivity(intent)
    }
    fun goToClassification(){
        val intent = Intent(context, classify_painting::class.java)
        startActivity(intent)
    }

}