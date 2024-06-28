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
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.ahmed.artify.Explore.ExploreActivity
import com.ahmed.artify.Helpers.ArtistLocal
import com.ahmed.artify.Helpers.Style
import com.ahmed.artify.R
import com.ahmed.artify.RetrofitClass.Api
import com.ahmed.artify.RetrofitClass.ApiRequests
import com.ahmed.artify.databinding.ActivityClassifyPaintingBinding
import com.ahmed.artify.ml.ArtistModel
import com.ahmed.artify.ml.StyleModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.util.Base64

class ClassifyPainting : AppCompatActivity() {

    private lateinit var binding: ActivityClassifyPaintingBinding
    private lateinit var bitmap: Bitmap
    private lateinit var result: Dialog
    private lateinit var imageProcessor: ImageProcessor
    private lateinit var artistLabels: List<String>
    private lateinit var styleLabels: List<String>
    lateinit var api:ApiRequests
    private var artistLocals:ArrayList<ArtistLocal> = ArrayList()
    private var styles: ArrayList<Style> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassifyPaintingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MainScope().launch(Dispatchers.IO){
            populateArtists()
            populateStyles()
            withContext(Dispatchers.Main){
                binding.classifyImageArtist.visibility = View.VISIBLE
                binding.classifyImageStyleButton.visibility = View.VISIBLE
                binding.classifyImageLinearLayout.visibility = View.VISIBLE
                binding.classifyImageProgressBar.visibility = View.GONE
            }
        }

        binding.classifyImageTitle.setOnClickListener {
            startActivity(Intent(this@ClassifyPainting, ExploreActivity::class.java))
        }

        artistLabels = application.assets.open("labels.txt").bufferedReader().readLines()
        styleLabels = application.assets.open("style_labels.txt").bufferedReader().readLines()
        imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(244, 244, ResizeOp.ResizeMethod.BILINEAR))
            .build()

        binding.classifyImageUploadButton.setOnClickListener {
            uploadImage()
        }
        binding.classifyImageUploaded.setOnClickListener {
            uploadImage()
        }

        binding.classifyImageArtist.setOnClickListener {
            showArtistResult(artistClassification(), artistLocals)
        }

        binding.classifyImageStyleButton.setOnClickListener {
            showStyleResult(styleClassification())
        }
    }

    private fun styleClassification(): String{
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
        model.close()
        return styleLabels[maxIdx]
    }

    private fun artistClassification():String{
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

        model.close()
        return artistLabels[maxIdx]
    }

    private fun showStyleResult(name: String) {

        result = Dialog(this)
        result.requestWindowFeature(Window.ID_ANDROID_CONTENT)
        result.setContentView(R.layout.classification_result)
        result.window?.setBackgroundDrawableResource(R.drawable.curved_white_primary_stroke)

        val styleImg = result.findViewById<ImageView>(R.id.result_image)
        val styleName = result.findViewById<TextView>(R.id.result_name)
        //val style_explore = result.findViewById<Button>(R.id.result_explore_button)
        val close = result.findViewById<ImageView>(R.id.result_close)

        val imgCard = result.findViewById<CardView>(R.id.result_artist_imagecard)

        val imageViewWidth: Int = imgCard.width
        val imageViewHeight: Int = imgCard.height
        if (imageViewWidth > 0 && imageViewHeight > 0) {
            bitmap = Bitmap.createScaledBitmap(bitmap, imageViewWidth, imageViewHeight, true)
        }

        // Set the scaled bitmap to the ImageView
        styleImg.setImageBitmap(bitmap)

        styleName.text = name

        close.setOnClickListener {
            binding.classifyImageUploaded.visibility = View.INVISIBLE
            binding.classifyImageLinearLayout.visibility = View.VISIBLE
            result.dismiss()
        }
        result.show()
    }

    private fun decodeImage(base64Img: String?): ByteArray? {
        return Base64.getDecoder().decode(base64Img)
    }

    private fun showArtistResult(name: String, artistLocals: ArrayList<ArtistLocal>) {

        result = Dialog(this)
        result.requestWindowFeature(Window.ID_ANDROID_CONTENT)
        result.setContentView(R.layout.classification_result)
        result.window?.setBackgroundDrawableResource(R.drawable.curved_white_primary_stroke)

        val artistImg = result.findViewById<ImageView>(R.id.result_image)
        val artistName = result.findViewById<TextView>(R.id.result_name)
        //val artist_explore = result.findViewById<Button>(R.id.result_explore_button)
        val close = result.findViewById<ImageView>(R.id.result_close)
        lateinit var artistLocal: ArtistLocal

        for(a in artistLocals){
            if(a.name == name){
                artistLocal = a
                break
            }
        }
        artistImg.setImageBitmap(artistLocal.image)
        artistName.text = name

        close.setOnClickListener {
            binding.classifyImageUploaded.visibility = View.INVISIBLE
            binding.classifyImageLinearLayout.visibility = View.VISIBLE
            result.dismiss()
        }

        result.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100){
            val uri = data?.data
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,uri)
            binding.classifyImageUploaded.setImageBitmap(bitmap)
            binding.classifyImageUploaded.visibility = View.VISIBLE
            binding.classifyImageLinearLayout.visibility = View.INVISIBLE
        }
    }

    private fun uploadImage(){
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent, 100)
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
                    styles.add(Style(name, "",image))
                }
            }
        }catch (e:Exception){
            Log.i("backend error", e.message!!)
            populateStyles()
        }
    }
}