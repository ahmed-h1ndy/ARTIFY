package com.ahmed.artify.RetrofitClass

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://192.168.1.102:5000"


private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
interface ApiService {
    @GET("get_all_artists")
    suspend fun get_all_artists():Response<ArtistData>

    @GET("get_all_styles")
    suspend fun get_all_styles():Response<ArtStyleData>
}
object Api {
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}