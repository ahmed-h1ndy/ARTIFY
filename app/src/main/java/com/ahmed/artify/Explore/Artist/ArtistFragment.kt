package com.ahmed.artify.Explore.Artist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.artify.Helpers.ArtistLocal
import com.ahmed.artify.R
import com.ahmed.artify.RetrofitClass.Api
import com.ahmed.artify.artsy.Artists.Artist
import com.ahmed.artify.artsy.ArtsyApiObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Base64
import kotlin.random.Random


class artist_fragment : Fragment(R.layout.fragment_artist) {

    lateinit var artist_recycler: RecyclerView
    var artistLocals: ArrayList<ArtistLocal> = ArrayList()
    var artists: List<Artist> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        artist_recycler = view.findViewById(R.id.artist_recycler)
        MainScope().launch(Dispatchers.IO) {
            /*
            populateArtists()
            withContext(Dispatchers.Main) {
                artist_recycler.adapter = ArtistsAdapter(artistLocals, requireContext())
                artist_recycler.layoutManager = GridLayoutManager(context, 3)

             */
            populateArtsyArtists()
            withContext(Dispatchers.Main) {
                artist_recycler.adapter = ArtistsAdapter2(artists, requireContext())
                artist_recycler.layoutManager = GridLayoutManager(context, 3)

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

    private suspend fun populateArtsyArtists(){
        try{
            val random = Random.nextInt(100)*20

            val response = ArtsyApiObject.retrofitService.getArtists(20, random, true, "-trending")
            if(response.isSuccessful){
                artists = response.body()?.embedded!!.artists

            }
        }catch (e:Exception){
            Log.i("backend error", e.message!!)
            populateArtsyArtists()
        }
    }

    private fun decodeImage(base64Img: String?): ByteArray? {
        return Base64.getDecoder().decode(base64Img)
    }
}