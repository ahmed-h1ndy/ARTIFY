package com.ahmed.artify.Game.Artist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ahmed.artify.Helpers.Artist
import com.ahmed.artify.RetrofitClass.Api
import com.ahmed.artify.databinding.ActivityGameArtistBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Base64
import java.util.Random

class GameArtist : AppCompatActivity() {

    private var artists: ArrayList<Artist> = ArrayList()
    private var allOptions: List<String> = ArrayList()
    private var answers: ArrayList<String> = ArrayList()
    private var score: Int = 0

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

    private fun startGame(index:Int) {
        if(index==10){
            //show result
            return
        }

        getAnswers(index)
        populateAnswers()

        binding.quizArtistQuestionImg.setImageBitmap(artists[index].image)

        binding.quizArtistAnswer1.setOnClickListener{
            checkAnswer(binding.quizArtistAnswer1.text.toString(), index)
        }

        binding.quizArtistAnswer2.setOnClickListener{
            checkAnswer(binding.quizArtistAnswer2.text.toString(), index)
        }

        binding.quizArtistAnswer3.setOnClickListener{
            checkAnswer(binding.quizArtistAnswer3.text.toString(), index)
        }
    }

    private fun checkAnswer(answer: String, index: Int) {
        if(answer == artists[index].name){
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

        answers.add(artists[index].name)
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
                    artists.add(Artist(name, image))
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