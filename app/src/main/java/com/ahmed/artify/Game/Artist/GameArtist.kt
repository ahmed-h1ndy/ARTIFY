package com.ahmed.artify.Game.Artist

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
import com.ahmed.artify.R
import com.ahmed.artify.RetrofitClass.Api
import com.ahmed.artify.databinding.ActivityGameArtistBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Base64
import java.util.Random

class GameArtist : AppCompatActivity() {

    private var artistLocals: ArrayList<ArtistLocal> = ArrayList()
    private var allOptions: List<String> = ArrayList()
    private var answers: ArrayList<String> = ArrayList()
    private var score: Int = 0
    private var askedQuestions = 0

    lateinit var binding: ActivityGameArtistBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameArtistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        allOptions = application.assets.open("labels.txt").bufferedReader().readLines()

        MainScope().launch(Dispatchers.IO) {
            populateArtists()
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

        binding.quizArtistProgressBar.max = 0
        var currentProgress = 0
        ObjectAnimator.ofInt(binding.quizArtistProgressBar, "progress", currentProgress)
            .setDuration(100)
            .start()


        binding.quizArtistProgressBar.max = 1000
        currentProgress = 1000
        ObjectAnimator.ofInt(binding.quizArtistProgressBar, "progress", currentProgress)
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

        binding.quizArtistAnswer1.background = ContextCompat.getDrawable(this, R.drawable.curved_white_primary_stroke_small)
        binding.quizArtistAnswer2.background = ContextCompat.getDrawable(this, R.drawable.curved_white_primary_stroke_small)
        binding.quizArtistAnswer3.background = ContextCompat.getDrawable(this, R.drawable.curved_white_primary_stroke_small)
        binding.quizArtistScore.text = "$score / ${askedQuestions++}"

        getAnswers(index)
        populateAnswers()

        binding.quizArtistQuestionImg.setImageBitmap(artistLocals[index].image)

        binding.quizArtistAnswer1.setOnClickListener{

            MainScope().launch {
                checkAnswer(
                    binding.quizArtistAnswer1.text.toString(),
                    index,
                    binding.quizArtistAnswer1
                )
            }
        }

        binding.quizArtistAnswer2.setOnClickListener{

            MainScope().launch {
                checkAnswer(
                    binding.quizArtistAnswer2.text.toString(),
                    index,
                    binding.quizArtistAnswer2)
            }
        }

        binding.quizArtistAnswer3.setOnClickListener{

            MainScope().launch {
                checkAnswer(
                    binding.quizArtistAnswer3.text.toString(),
                    index,
                    binding.quizArtistAnswer3)
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
        if(answer == artistLocals[index].name){

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
        binding.quizArtistAnswer1.text = s

        s = answers.removeAt(Random().nextInt(answers.size))
        binding.quizArtistAnswer2.text = s

        s = answers.removeAt(Random().nextInt(answers.size))
        binding.quizArtistAnswer3.text = s
    }

    private fun getAnswers(index:Int) {

        answers.clear()

        answers.add(artistLocals[index].name)
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

    private suspend fun populateArtists(){
        try{
            val response = Api.retrofitService.get_all_artists()
            if(response.isSuccessful){
                val tempArtists = response.body()?.artistList

                var name: String
                var image: Bitmap?
                for (i in tempArtists!!.indices) {
                    name = tempArtists[i].name.toString()
                    val bytes: ByteArray = decodeImage(tempArtists[i].a_image)!!
                    image = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    artistLocals.add(ArtistLocal(name, image))
                }
            }
        }catch (e:Exception){
            Log.i("backend error", e.message!!)
            populateArtists()
        }
    }

    private fun decodeImage(base64Img: String?): ByteArray? {
        return Base64.getDecoder().decode(base64Img)
    }
}








/*

questions = list[artistImagesWithLabels]
options = list[artistNames]
while(10--){

    show img from questions
    get 2 random answers from options + the correct option
    make the progress bar full and start running it out
    update question counter

}

*optional* -> show result in a popup



 */