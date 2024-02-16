package com.ahmed.artify.explore;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequests {
    final  Retrofit retrofit;
    final ApiInterface apiInterface;
    Call<PaintingData> callPainting;
    Call<ArtistData> callArtist;
    Call<ArtStyleData>callArtStyle;
    public ApiRequests() {
         retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.1.3:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface=retrofit.create(ApiInterface.class);


    }

    List<Painting> paintingArrayList;

    public void getPaintById(int id) {
        callPainting =apiInterface.get_paint(id);

        callPainting.enqueue(new Callback<PaintingData>() {
            @Override
            public void onResponse(Call<PaintingData> call, Response<PaintingData> response) {


                paintingArrayList=response.body().getDataList();

                Log.i("style id",String.valueOf(paintingArrayList.get(0).getP_art_style_id()));
                Log.i("id",String.valueOf(paintingArrayList.get(0).getP_id()));
                Log.i("image",paintingArrayList.get(0).getP_image());
            }

            @Override
            public void onFailure(Call<PaintingData> call, Throwable t) {
                System.out.println(t.getMessage());
                System.out.println(t.getStackTrace().toString());
            }
        });
      //  return paintingArrayList.get(0);


    }

    public List<Painting> getAllPaintings(TextView textView, ImageView image)
    {

        callPainting =apiInterface.get_all_paints();

                  callPainting.enqueue(new Callback<PaintingData>() {
                    @Override
                    public void onResponse(Call<PaintingData> call, Response<PaintingData> response) {


                        paintingArrayList=response.body().getDataList();
                        Log.i("style id",String.valueOf(paintingArrayList.get(0).getP_art_style_id()));
                        Log.i("id",String.valueOf(paintingArrayList.get(0).getP_id()));
                        Log.i("image",paintingArrayList.get(0).getP_image());
                        textView.setText(String.valueOf(paintingArrayList.get(2).getP_art_style_id()));
                        byte[] Bytes= decodeImage(paintingArrayList.get(1).getP_image());
                        Bitmap decodedImage = BitmapFactory.decodeByteArray(Bytes, 0, Bytes.length);

                        // Display the Bitmap in an ImageView
                        image.setImageBitmap(decodedImage);
                    }

                    @Override
                    public void onFailure(Call<PaintingData> call, Throwable t) {
                        System.out.println(t.getMessage());
                        System.out.println(t.getStackTrace().toString());
                    }
                });



        return paintingArrayList;

    }

    public List<Painting> getAllPaintingsByStyleId(int id)
    {
        callPainting =apiInterface.get_paintings_by_style_id(id);
        callPainting.enqueue(new Callback<PaintingData>() {
            @Override
            public void onResponse(Call<PaintingData> call, Response<PaintingData> response) {


                paintingArrayList=response.body().getDataList();

                Log.i("style id",String.valueOf(paintingArrayList.get(0).getP_art_style_id()));
                Log.i("id",String.valueOf(paintingArrayList.get(0).getP_id()));
                Log.i("image",paintingArrayList.get(0).getP_image());

            }

            @Override
            public void onFailure(Call<PaintingData> call, Throwable t) {
                System.out.println(t.getMessage());
                System.out.println(t.getStackTrace().toString());
            }
        });
        return paintingArrayList;

    }
    public List<Painting> getAllPaintingsByArtistId(int id)
    {
        callPainting =apiInterface.get_paintings_by_artist_id(id);
        callPainting.enqueue(new Callback<PaintingData>() {
            @Override
            public void onResponse(Call<PaintingData> call, Response<PaintingData> response) {


                paintingArrayList=response.body().getDataList();
                Log.i("style id",String.valueOf(paintingArrayList.get(0).getP_art_style_id()));
                Log.i("id",String.valueOf(paintingArrayList.get(0).getP_id()));
                Log.i("image",paintingArrayList.get(0).getP_image());

            }

            @Override
            public void onFailure(Call<PaintingData> call, Throwable t) {
                System.out.println(t.getMessage());
                System.out.println(t.getStackTrace().toString());
            }
        });
        return paintingArrayList;
    }
    private List<Artist> ArtistArrayList;
    public  List<Artist> getAllArtists()
    {
        callArtist =apiInterface.get_all_artists();
        callArtist.enqueue(new Callback<ArtistData>() {
            @Override
            public void onResponse(Call<ArtistData> call, Response<ArtistData> response) {


                ArtistArrayList=response.body().getArtistList();

                Log.i("ArtistName",String.valueOf(ArtistArrayList.get(0).getName()));
                Log.i("Artist id",String.valueOf(ArtistArrayList.get(0).getA_id()));
                Log.i("Artist image",ArtistArrayList.get(0).getA_image());

            }

            @Override
            public void onFailure(Call<ArtistData> call, Throwable t) {
                System.out.println(t.getMessage());
                System.out.println(t.getStackTrace().toString());
            }
        });
        return ArtistArrayList;

    }
    private List<ArtStyle> ArtStyleArrayList;
    public List<ArtStyle> getAllArtStyles()
    {
        callArtStyle =apiInterface.get_all_styles();
        callArtStyle.enqueue(new Callback<ArtStyleData>() {
            @Override
            public void onResponse(Call<ArtStyleData> call, Response<ArtStyleData> response) {


                ArtStyleArrayList=response.body().getArtStyleList();
                Log.i("style name",String.valueOf(ArtStyleArrayList.get(0).getS_name()));
                Log.i("style id",String.valueOf(ArtStyleArrayList.get(0).getS_id()));
                Log.i("image",ArtStyleArrayList.get(0).getS_image());


            }

            @Override
            public void onFailure(Call<ArtStyleData> call, Throwable t) {
                System.out.println(t.getMessage());
                System.out.println(t.getStackTrace().toString());
            }
        });
        return ArtStyleArrayList;
    }
    public  byte[] decodeImage(String base64Img)
    {
        byte[] imageByte;



        return Base64.getDecoder().decode(base64Img);
    }
}
