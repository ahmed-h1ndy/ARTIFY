package com.ahmed.artify.Explore.Style

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.artify.Helpers.Style
import com.ahmed.artify.R
import com.ahmed.artify.RetrofitClass.Api
import com.ahmed.artify.RetrofitClass.ApiRequests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Base64


class style_fragment : Fragment(R.layout.fragment_style) {

    lateinit var style_recycler: RecyclerView
    var styles: ArrayList<Style> = ArrayList()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        style_recycler = view.findViewById(R.id.style_recycler)
        MainScope().launch(Dispatchers.IO) {
            populateStyles()
            withContext(Dispatchers.Main) {
                style_recycler.visibility = View.VISIBLE
                view.findViewById<ProgressBar>(R.id.style_loading).visibility = View.GONE
                style_recycler.adapter = StylesAdapter(styles, requireContext())
                style_recycler.layoutManager = GridLayoutManager(context, 2)
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
                    styles.add(Style(name, "",image))
                }
            }
        }catch (e:Exception){
            Log.i("backend error", e.message!!)
            populateStyles()
        }
    }

    private fun decodeImage(base64Img: String?): ByteArray? { return Base64.getDecoder().decode(base64Img) }

}