package com.ahmed.artify.RetrofitClass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ahmed.artify.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Trial : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trial)


        GlobalScope.launch {

            try{

                val api = APIRequests()
                val response = api.retrofitAPI.get_all_artists()
                if(response.isSuccessful){
                var artists = response.body()?.artistList

                if(artists!!.size>0)
                    for(ar in artists)
                Log.i("hoba lala", ar.name)
                }

                else{

                }
            }catch (e:Exception){
                Log.i("hoba lala", e.message!!)
            }

        }
    }
}