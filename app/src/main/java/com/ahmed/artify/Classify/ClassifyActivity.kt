package com.ahmed.artify.Classify

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.ahmed.artify.Explore.ExploreActivity
import com.ahmed.artify.Helpers.Artist
import com.ahmed.artify.Helpers.Style
import com.ahmed.artify.R
import com.ahmed.artify.RetrofitClass.APIRequests
import com.ahmed.artify.RetrofitClass.ApiRequests
import com.ahmed.artify.RetrofitClass.ArtArtist
import com.ahmed.artify.ml.ArtistModel
import com.ahmed.artify.ml.StyleModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import retrofit2.awaitResponse
import java.util.Base64

class classify_painting : AppCompatActivity() {

    lateinit var upload_button: ImageView
    lateinit var artist_predict_button: Button
    lateinit var style_predict_button: Button
    lateinit var image_uploaded: ImageView
    lateinit var upload_linear_layout: LinearLayout
    lateinit var bitmap: Bitmap
    lateinit var classify_image_title:TextView
    lateinit var result: Dialog
    //lateinit var api: ApiRequests
    lateinit var API:ApiRequests
    lateinit var call:Job
    var artists:ArrayList<Artist> = ArrayList()
    var styles: ArrayList<Style> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classify_painting)

        upload_button = findViewById(R.id.classify_image_upload_button)
        artist_predict_button = findViewById(R.id.classify_image_artist)
        style_predict_button = findViewById(R.id.classify_image_style_button)
        image_uploaded = findViewById(R.id.classify_image_uploaded)
        upload_linear_layout = findViewById(R.id.classify_image_upload_image)

        //API = ApiRequests()

        call = GlobalScope.launch(Dispatchers.IO){
            //artists = API.initialize_artists{}

            try{

                val api = APIRequests()
                val response = api.retrofitAPI.get_all_artists()
                if(response.isSuccessful){
                    var temp_artists = response.body()?.artistList

                    var name: String
                    var image: Bitmap?
                    for (i in temp_artists!!.indices) {
                        name = temp_artists[i].name.toString()
                        val Bytes: ByteArray = decodeImage(temp_artists[i].a_image)!!
                        image = BitmapFactory.decodeByteArray(Bytes, 0, Bytes.size)
                        artists.add(Artist(name, image))
                    }
                }

                else{

                }
            }catch (e:Exception){
                Log.i("hoba lala", e.message!!)
            }


            /*val callArtist = API.apiInterface._all_artists
            val temp_artists: List<ArtArtist>? = callArtist.awaitResponse().body()?.artistList
            var name: String
            var image: Bitmap?
            for (i in temp_artists!!.indices) {
                name = temp_artists[i].name.toString()
                val Bytes: ByteArray = decodeImage(temp_artists[i].a_image)!!
                image = BitmapFactory.decodeByteArray(Bytes, 0, Bytes.size)
                artists.add(Artist(name, image))
            }
*/
            //styles = API.initialize_styles {}
        }
        /*api = ApiRequests()
        if(api.artists==null){
            api.initialize_artists {  }
        }
        if(api.styles==null){
            api.initialize_styles {  }
        }*/

        classify_image_title=findViewById(R.id.classify_image_title)
        classify_image_title.setOnClickListener {
            startActivity(Intent(this@classify_painting, ExploreActivity::class.java))
        }


        var artist_labels = application.assets.open("labels.txt").bufferedReader().readLines()
        var style_labels = application.assets.open("style_labels.txt").bufferedReader().readLines()

        var imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(244, 244, ResizeOp.ResizeMethod.BILINEAR))
            .build()

        upload_button.setOnClickListener {
            upload_image()
        }
        image_uploaded.setOnClickListener {
            upload_image()
        }

        artist_predict_button.setOnClickListener {

            var tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(bitmap)
            tensorImage = imageProcessor.process(tensorImage)

            val model = ArtistModel.newInstance(this)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 244, 244, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(tensorImage.buffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

            var maxIdx = 0
            outputFeature0.forEachIndexed{index, fl ->
                if(outputFeature0[maxIdx]<fl){
                    maxIdx = index
                }
            }

            if(artists.isNotEmpty()){
                show_artist_result(artist_labels[maxIdx], artists)
            }
            else{
                GlobalScope.launch {
                    //artists = API.initialize_artists {  }

                    try{

                        val api = APIRequests()
                        val response = api.retrofitAPI.get_all_artists()
                        if(response.isSuccessful){
                            var temp_artists = response.body()?.artistList

                            var name: String
                            var img: Bitmap?
                            for (i in temp_artists!!.indices) {
                                name = temp_artists[i].name.toString()
                                val Bytes: ByteArray = decodeImage(temp_artists[i].a_image)!!
                                img = BitmapFactory.decodeByteArray(Bytes, 0, Bytes.size)
                                artists.add(Artist(name, img))
                            }
                        }

                        else{

                        }
                    }catch (e:Exception){
                        Log.i("hoba lala", e.message!!)
                    }
                    withContext(Dispatchers.Main){
                        show_artist_result(artist_labels[maxIdx], artists)
                    }

                }

            }

            model.close()
        }

        style_predict_button.setOnClickListener {

            var tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(bitmap)
            tensorImage = imageProcessor.process(tensorImage)

            val model = StyleModel.newInstance(this)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 244, 244, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(tensorImage.buffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

            var maxIdx = 0
            outputFeature0.forEachIndexed{index, fl ->
                if(outputFeature0[maxIdx]<fl){
                    maxIdx = index
                }
            }

            if(styles!=null){
            show_style_result(style_labels[maxIdx], styles)
            }
            else{
                GlobalScope.launch {
                    styles = API.initialize_styles {  }
                    show_style_result(style_labels[maxIdx], styles)
                }

            }
            model.close()
        }



    }

    private fun show_style_result(name: String, styles: ArrayList<Style>) {

        result = Dialog(this)
        result.requestWindowFeature(Window.ID_ANDROID_CONTENT)
        result.setContentView(R.layout.classification_result)
        result.window?.setBackgroundDrawableResource(R.drawable.curved_white_primary_stroke)

        val style_img = result.findViewById<ImageView>(R.id.result_image)
        val style_name = result.findViewById<TextView>(R.id.result_name)
        val style_explore = result.findViewById<Button>(R.id.result_explore_button)
        val close = result.findViewById<ImageView>(R.id.result_close)

        val img_card = result.findViewById<CardView>(R.id.result_artist_imagecard)

        val imageViewWidth: Int = img_card.width
        val imageViewHeight: Int = img_card.height
        if (imageViewWidth > 0 && imageViewHeight > 0) {
            bitmap = Bitmap.createScaledBitmap(bitmap, imageViewWidth, imageViewHeight, true)
        }

        // Set the scaled bitmap to the ImageView
        style_img.setImageBitmap(bitmap)

        style_name.text = name

        close.setOnClickListener {
            image_uploaded.visibility = View.INVISIBLE
            upload_linear_layout.visibility = View.VISIBLE
            result.dismiss()
        }

        result.show()
    }

    fun decodeImage(base64Img: String?): ByteArray? {
        var imageByte: ByteArray
        return Base64.getDecoder().decode(base64Img)
    }

    private fun show_artist_result(name: String, artists: ArrayList<Artist>) {

        result = Dialog(this)
        result.requestWindowFeature(Window.ID_ANDROID_CONTENT)
        result.setContentView(R.layout.classification_result)
        result.window?.setBackgroundDrawableResource(R.drawable.curved_white_primary_stroke)

        val artist_img = result.findViewById<ImageView>(R.id.result_image)
        val artist_name = result.findViewById<TextView>(R.id.result_name)
        val artist_explore = result.findViewById<Button>(R.id.result_explore_button)
        val close = result.findViewById<ImageView>(R.id.result_close)
        lateinit var artist: Artist

        for(a in artists){
            if(a.name.equals(name)){
                artist = a
                break
            }
        }
        artist_img.setImageBitmap(artist.image)
        artist_name.text = name

        close.setOnClickListener {
            image_uploaded.visibility = View.INVISIBLE
            upload_linear_layout.visibility = View.VISIBLE
            result.dismiss()
        }

        result.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100){
            var uri = data?.data
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,uri)
            image_uploaded.setImageBitmap(bitmap)
            image_uploaded.visibility = View.VISIBLE
            upload_linear_layout.visibility = View.INVISIBLE
        }
    }


    fun upload_image(){
        var intent: Intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }
}