package com.ahmed.artify.artsy

import com.ahmed.artify.artsy.Artists.Artist
import com.ahmed.artify.artsy.Artists.ArtistResponse
import com.ahmed.artify.artsy.Artwork.ArtworksResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.artsy.net/api/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    interface ArtsyApi {
        @GET("artists")
        @Headers("X-XAPP-Token: eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6IiIsInN1YmplY3RfYXBwbGljYXRpb24iOiI0NGNhOTVjZS1iNWJhLTRhNDUtYTFlZC1hOWU1MDE3Nzg2YjkiLCJleHAiOjE3MjAxMTA1ODAsImlhdCI6MTcxOTUwNTc4MCwiYXVkIjoiNDRjYTk1Y2UtYjViYS00YTQ1LWExZWQtYTllNTAxNzc4NmI5IiwiaXNzIjoiR3Jhdml0eSIsImp0aSI6IjY2N2Q5Mzc0OGY1ODljMDAwZmUwMTVmOCJ9.LtDKoFNy1fnpAuRq6fq9D-3iKRipcScvcxBecvw_1N8")
        suspend fun getArtists(@Query("size") size: Int = 10,
                               @Query("offset") offset: Int = 0,
                               @Query("artworks") artworks: Boolean = true,
                               @Query("sort") sort:String = "-trending"): Response<ArtistResponse>

        @GET("artworks")
        @Headers("X-XAPP-Token: eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6IiIsInN1YmplY3RfYXBwbGljYXRpb24iOiI0NGNhOTVjZS1iNWJhLTRhNDUtYTFlZC1hOWU1MDE3Nzg2YjkiLCJleHAiOjE3MjAxMTA1ODAsImlhdCI6MTcxOTUwNTc4MCwiYXVkIjoiNDRjYTk1Y2UtYjViYS00YTQ1LWExZWQtYTllNTAxNzc4NmI5IiwiaXNzIjoiR3Jhdml0eSIsImp0aSI6IjY2N2Q5Mzc0OGY1ODljMDAwZmUwMTVmOCJ9.LtDKoFNy1fnpAuRq6fq9D-3iKRipcScvcxBecvw_1N8")
        suspend fun getArtworks(@Query("size") size: Int = 3,
                                @Query("artist_id") artist_id: String): Response<ArtworksResponse>

        @GET("artists/{id}")
        @Headers("X-XAPP-Token: eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6IiIsInN1YmplY3RfYXBwbGljYXRpb24iOiI0NGNhOTVjZS1iNWJhLTRhNDUtYTFlZC1hOWU1MDE3Nzg2YjkiLCJleHAiOjE3MjAxMTA1ODAsImlhdCI6MTcxOTUwNTc4MCwiYXVkIjoiNDRjYTk1Y2UtYjViYS00YTQ1LWExZWQtYTllNTAxNzc4NmI5IiwiaXNzIjoiR3Jhdml0eSIsImp0aSI6IjY2N2Q5Mzc0OGY1ODljMDAwZmUwMTVmOCJ9.LtDKoFNy1fnpAuRq6fq9D-3iKRipcScvcxBecvw_1N8")
        suspend fun getArtworkById(@Path("id") id: String): Response<Artist>
    }
    object ArtsyApiObject {
        val retrofitService : ArtsyApi by lazy {
            retrofit.create(ArtsyApi::class.java)
        }
}