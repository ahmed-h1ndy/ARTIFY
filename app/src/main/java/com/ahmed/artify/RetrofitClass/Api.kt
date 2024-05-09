package com.ahmed.artify.RetrofitClass

import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("get_all_artists")
    suspend fun get_all_artists():Response<ArtistData>

    @GET("get_all_styles")
    suspend fun get_all_styles():Response<ArtStyleData>
}