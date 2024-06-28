package com.ahmed.artify.ExploreExpand

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ahmed.artify.R
import com.ahmed.artify.RetrofitClass.Api
import com.ahmed.artify.RetrofitClass.ApiRequests
import com.ahmed.artify.artsy.ArtsyApiObject
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Base64

class ExploreExpandActivity : AppCompatActivity() {

    lateinit var big_image:ImageView
    lateinit var example_image_1:ImageView
    lateinit var example_image_2:ImageView
    lateinit var example_name_1:TextView
    lateinit var example_name_2:TextView
    private lateinit var name:TextView
    lateinit var description:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore_expand)

        val type = intent.getStringExtra("type")
        big_image = findViewById(R.id.expand_big_image)
        example_image_1 = findViewById<View>(R.id.expand_example_1).findViewById<ImageView>(R.id.expand_example)
        example_image_2 = findViewById<View>(R.id.expand_example_2).findViewById<ImageView>(R.id.expand_example)
        example_name_1 = findViewById<View>(R.id.expand_example_1).findViewById<TextView>(R.id.expand_painting_name)
        example_name_2 = findViewById<View>(R.id.expand_example_2).findViewById<TextView>(R.id.expand_painting_name)
        name = findViewById(R.id.expand_name)
        description = findViewById(R.id.expand_details)

        if(type.equals("style")){
            MainScope().launch {
                fill_japanese()
            }

        }
        else{
            fill_artist(type!!)
        }
    }

    private fun fill_artist(id: String) {

        MainScope().launch {
            try{
                val res = ArtsyApiObject.retrofitService.getArtworks(3, id)
                if(res.isSuccessful){

                    withContext(Dispatchers.Main){
                        Glide.with(example_image_1.context)
                            .load(res.body()?._embedded!!.artworks[0]._links.thumbnail.href)
                            .into(example_image_1)

                        Glide.with(example_image_2.context)
                            .load(res.body()?._embedded!!.artworks[1]._links.thumbnail.href)
                            .into(example_image_2)

                        Glide.with(big_image.context)
                            .load(res.body()?._embedded!!.artworks[2]._links.thumbnail.href)
                            .into(big_image)
                    }

                    example_name_1.text = res.body()?._embedded!!.artworks[0].title
                    example_name_2.text = res.body()?._embedded!!.artworks[1].title

                    try {
                        val artistRes = ArtsyApiObject.retrofitService.getArtworkById(id)
                        if (artistRes.isSuccessful) {

                            name.text = artistRes.body()?.name

                            description.text =
                                "Hometown : ${artistRes.body()?.hometown}\nBirthday : ${artistRes.body()?.birthday}\nDeathday : ${artistRes.body()?.deathday}\n"
                        }
                    }catch(e:Exception){
                        Log.i("backend error baby", e.toString())
                    }
                }
            }catch(e:Exception){
                Log.i("backend error", e.toString())
            }
        }


        /*example_image_1.setImageResource(R.drawable.van_gogh_2)
        example_image_2.setImageResource(R.drawable.van_gogh_1)
        example_name_1.text = "Fishing Boats on the Beach"
        example_name_2.text = "The Starry Night"
        name.text = "Vincent van Gogh"
        description.text = "Vincent van Gogh was a Dutch Post-Impressionist painter who is considered one of the greatest artists of all time. He was born on March 30, 1853, in Zundert, Netherlands, and tragically died by suicide at the age of 37 on July 29, 1890. Van Gogh's work is known for its emotional honesty, bold colors, and expressive brushwork."
   */


    }

    private suspend fun fill_japanese() {

        val api = Api.retrofitService.get_all_styles()
        val bytes: ByteArray = decodeImage(api.body()!!.artStyleList[2].s_image)!!
        val image = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        big_image.setImageBitmap(image)
        example_image_1.setImageResource(R.drawable.japanese_1)
        example_image_2.setImageResource(R.drawable.japanese_2)
        example_name_1.text = "The Great Wave off Kanagawa"
        example_name_2.text = "Fuji from Kawaguchi Lake"
        name.text = "Japanese Art"
        description.text = "Japanese art consists of a wide range of art styles and media that includes ancient pottery, sculpture, ink painting and calligraphy on silk and paper, ukiyo-e paintings and woodblock prints, ceramics, origami, bonsai, and more recently manga and anime. It has a long history, ranging from the beginnings of human habitation in Japan, sometime in the 10th millennium BCE, to the present day."
    }

    private fun decodeImage(base64Img: String?): ByteArray? { return Base64.getDecoder().decode(base64Img) }
}