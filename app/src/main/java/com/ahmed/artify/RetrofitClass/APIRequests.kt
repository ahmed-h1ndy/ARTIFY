package com.ahmed.artify.RetrofitClass

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIRequests {

    var retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("http://192.168.1.102:5000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var retrofitAPI : Api = retrofit.create(Api::class.java)

    constructor()
}