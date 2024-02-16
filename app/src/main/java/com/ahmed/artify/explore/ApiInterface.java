package com.ahmed.artify.explore;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("get_all_paintings")
    public Call<PaintingData> get_all_paints();
    @GET("get_paint/{id}")
    public Call<PaintingData> get_paint(@Path("id") int paintingID);

    @GET("get_style/{id}")
    public Call<PaintingData> get_paintings_by_style_id(@Path("id") int styleID);

    @GET("get_artist/{id}")
    public Call<PaintingData> get_paintings_by_artist_id(@Path("id") int artistID);

    @GET("get_all_styles")
    public Call<ArtStyleData> get_all_styles();

    @GET("get_all_artists")
    public Call<ArtistData> get_all_artists();

}
