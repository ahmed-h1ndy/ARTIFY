package com.ahmed.artify

import android.content.Intent
import android.graphics.Bitmap
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.ahmed.artify.explore.ExploreActivity
import com.ahmed.artify.ml.ArtistModel
import com.ahmed.artify.ml.StyleModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class classify_painting : AppCompatActivity() {

    lateinit var upload_button: ImageView
    lateinit var artist_predict_button: Button
    lateinit var style_predict_button: Button
    lateinit var image_uploaded: ImageView
    lateinit var upload_linear_layout: LinearLayout
    lateinit var bitmap: Bitmap
    lateinit var classify_image_title:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classify_painting)

        upload_button = findViewById(R.id.classify_image_upload_button)
        artist_predict_button = findViewById(R.id.classify_image_artist)
        style_predict_button = findViewById(R.id.classify_image_style_button)
        image_uploaded = findViewById(R.id.classify_image_uploaded)
        upload_linear_layout = findViewById(R.id.classify_image_upload_image)


        classify_image_title=findViewById(R.id.classify_image_title)
        classify_image_title.setOnClickListener {
            startActivity(Intent(this@classify_painting,ExploreActivity::class.java))
        }


        var artist_labels = application.assets.open("labels.txt").bufferedReader().readLines()
        var style_labels = application.assets.open("style_labels.txt").bufferedReader().readLines()

        var imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(244, 244, ResizeOp.ResizeMethod.BILINEAR))
            .build()

        upload_button.setOnClickListener {
            var intent: Intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 100)
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
            Toast.makeText(this, artist_labels[maxIdx],Toast.LENGTH_SHORT).show()

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
            Toast.makeText(this, style_labels[maxIdx],Toast.LENGTH_SHORT).show()

            model.close()
        }

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
}