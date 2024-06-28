package com.ahmed.artify.Game.Style

import android.animation.ObjectAnimator
import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.ahmed.artify.Helpers.ArtistLocal
import com.ahmed.artify.Helpers.Style
import com.ahmed.artify.R
import com.ahmed.artify.RetrofitClass.Api
import com.ahmed.artify.databinding.ActivityGameArtistBinding
import com.ahmed.artify.databinding.ActivityGameStyleBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Base64
import java.util.Random

class GameStyle : AppCompatActivity() {

    var styleLocals: ArrayList<Style> = ArrayList()
    private var allOptions: List<String> = ArrayList()
    private var answers: ArrayList<String> = ArrayList()
    private var score: Int = 0
    private var askedQuestions = 0

    lateinit var binding: ActivityGameStyleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameStyleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        allOptions = listOf("Art Nouveau",
            "Expressionism",
            "Japanese Art",
            "Neoclassicism",
            "Rococo",
            "Minimalism",
            "Pointillism",
            "Pop Art",
            "Romanticism",
            "Symbolism"
            )

        MainScope().launch(Dispatchers.IO) {
            populateStyles()
            withContext(Dispatchers.Main) {
                startGame(0)
            }
        }
    }

    private suspend fun startGame(index:Int) {

        if(index==10){
            show_result()
            return
        }

        binding.quizStyleProgressBar.max = 0
        var currentProgress = 0
        ObjectAnimator.ofInt(binding.quizStyleProgressBar, "progress", currentProgress)
            .setDuration(100)
            .start()


        binding.quizStyleProgressBar.max = 1000
        currentProgress = 1000
        ObjectAnimator.ofInt(binding.quizStyleProgressBar, "progress", currentProgress)
            .setDuration(5000)
            .start()

        MainScope().launch {
            delay(5000)
            Log.i("hoba lala", "index = $index, asked = $askedQuestions")
            if(index == askedQuestions-1&& askedQuestions!=10){
                Log.i("hoba lala", "index = $index, asked = $askedQuestions")
                startGame(index+1)
            }
        }

        binding.quizStyleAnswer1.background = ContextCompat.getDrawable(this, R.drawable.curved_white_primary_stroke_small)
        binding.quizStyleAnswer2.background = ContextCompat.getDrawable(this, R.drawable.curved_white_primary_stroke_small)
        binding.quizStyleAnswer3.background = ContextCompat.getDrawable(this, R.drawable.curved_white_primary_stroke_small)
        binding.quizStyleScore.text = "$score / ${askedQuestions++}"

        getAnswers(index)
        populateAnswers()

        binding.quizStyleQuestionImg.setImageBitmap(styleLocals[index].image)

        binding.quizStyleAnswer1.setOnClickListener{

            MainScope().launch {
                checkAnswer(
                    binding.quizStyleAnswer1.text.toString(),
                    index,
                    binding.quizStyleAnswer1
                )
            }
        }

        binding.quizStyleAnswer2.setOnClickListener{

            MainScope().launch {
                checkAnswer(
                    binding.quizStyleAnswer2.text.toString(),
                    index,
                    binding.quizStyleAnswer2)
            }
        }

        binding.quizStyleAnswer3.setOnClickListener{

            MainScope().launch {
                checkAnswer(
                    binding.quizStyleAnswer3.text.toString(),
                    index,
                    binding.quizStyleAnswer3)
            }
        }
    }

    private fun show_result() {
        val result = Dialog(this)
        result.requestWindowFeature(Window.ID_ANDROID_CONTENT)
        result.setContentView(R.layout.game_result)
        result.window?.setBackgroundDrawableResource(R.drawable.curved_white_primary_stroke)

        val final_score = result.findViewById<TextView>(R.id.game_result_score)
        val play_again = result.findViewById<Button>(R.id.game_result_play_again)

        final_score.text = "Score : $score / $askedQuestions"

        play_again.setOnClickListener {
            MainScope().launch {
                score = 0
                askedQuestions = 0
                startGame(0)
            }
            result.dismiss()
        }

        result.show()
    }

    private suspend fun checkAnswer(answer: String, index: Int, answerButton: Button) {
        if(answer == styleLocals[index].name){

            answerButton.background = ContextCompat.getDrawable(this, R.drawable.curved_white_green_stroke_small)
            score++
            delay(300)
            startGame(index+1)
        }
        else{
            answerButton.background = ContextCompat.getDrawable(this, R.drawable.curved_white_red_stroke_small)
            delay(300)
            startGame(index+1)
        }
    }

    private fun populateAnswers() {

        var s = answers.removeAt(Random().nextInt(answers.size))
        binding.quizStyleAnswer1.text = s

        s = answers.removeAt(Random().nextInt(answers.size))
        binding.quizStyleAnswer2.text = s

        s = answers.removeAt(Random().nextInt(answers.size))
        binding.quizStyleAnswer3.text = s
    }

    private fun getAnswers(index:Int) {

        answers.clear()

        answers.add(styleLocals[index].name)
        while(true){
            answers.add(allOptions[Random().nextInt(10)])
            if(answers[0]!=answers[1]) {
                break
            }
            else{
                answers.removeAt(1)
            }
        }
        while(true) {
            answers.add(allOptions[Random().nextInt(10)])
            if(answers[0]!=answers[2]&&answers[1]!=answers[2]){
                break
            }
            else{
                answers.removeAt(2)
            }
        }
    }

    private suspend fun populateStyles(){
        try{
            val response = Api.retrofitService.get_all_styles()
            if(response.isSuccessful){
                val tempStyles = response.body()?.artStyleList

                var name: String
                var image: Bitmap?
                for (i in tempStyles!!.indices) {
                    name = tempStyles[i].s_name.toString()
                    val bytes: ByteArray = decodeImage(tempStyles[i].s_image)!!
                    image = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    styleLocals.add(Style(name, "",image))
                }
            }
        }catch (e:Exception){
            Log.i("backend error", e.message!!)
            populateStyles()
        }
    }

    private fun decodeImage(base64Img: String?): ByteArray? { return Base64.getDecoder().decode(base64Img) }
}