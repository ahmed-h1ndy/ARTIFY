package com.ahmed.artify.explore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmed.artify.Artist;
import com.ahmed.artify.ArtistsAdapter;
import com.ahmed.artify.Style;
import com.ahmed.artify.StylesAdapter;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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
                .baseUrl("http://192.168.1.106:5000")
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

                        textView.setText(String.valueOf(paintingArrayList.get(1).getP_art_style_id()));
                        byte[] Bytes= decodeImage(paintingArrayList.get(0).getP_image());
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
    private List<ArtArtist> artArtistArrayList;
    public List<ArtArtist> getArtists(RecyclerView artist_recycler, Context context)
    {
        callArtist =apiInterface.get_all_artists();
        callArtist.enqueue(new Callback<ArtistData>() {
            @Override
            public void onResponse(Call<ArtistData> call, Response<ArtistData> response) {


                artArtistArrayList =response.body().getArtistList();

                Log.i("ArtistName",String.valueOf(artArtistArrayList.get(3).getName()));
                Log.i("Artist id",String.valueOf(artArtistArrayList.get(3).getA_id()));
                Log.i("Artist image", artArtistArrayList.get(3).getA_image());

                ArrayList<Artist> artists = new ArrayList<>();

                String name;
                Bitmap image;
                for(int i = 0;i<artArtistArrayList.size();i++){
                    name = String.valueOf(artArtistArrayList.get(i).getName());
                    byte[] Bytes= decodeImage(artArtistArrayList.get(i).getA_image());
                    image = BitmapFactory.decodeByteArray(Bytes, 0, Bytes.length);
                    artists.add(new Artist(name, image));
                }

                artist_recycler.setAdapter(new ArtistsAdapter(artists));
                artist_recycler.setLayoutManager(new GridLayoutManager(context,3));

            }

            @Override
            public void onFailure(Call<ArtistData> call, Throwable t) {
                System.out.println(t.getMessage());
                System.out.println(t.getStackTrace().toString());
            }
        });
        return artArtistArrayList;

    }

    private List<ArtStyle> ArtStyleArrayList;
    public List<ArtStyle> getAllArtStyles(RecyclerView style_recycler, Context context)
    {
        callArtStyle =apiInterface.get_all_styles();
        callArtStyle.enqueue(new Callback<ArtStyleData>() {
            @Override
            public void onResponse(Call<ArtStyleData> call, Response<ArtStyleData> response) {


                ArtStyleArrayList=response.body().getArtStyleList();
                Log.i("style name",String.valueOf(ArtStyleArrayList.get(0).getS_name()));
                Log.i("style id",String.valueOf(ArtStyleArrayList.get(0).getS_id()));
                Log.i("image",ArtStyleArrayList.get(0).getS_image());

                ArrayList<Style> styles = new ArrayList<>();

                String name;
                Bitmap image;
                for(int i = 0;i<ArtStyleArrayList.size();i++){
                    name = String.valueOf(ArtStyleArrayList.get(i).getS_name());
                    byte[] Bytes= decodeImage(ArtStyleArrayList.get(i).getS_image());
                    image = BitmapFactory.decodeByteArray(Bytes, 0, Bytes.length);
                    styles.add(new Style(name,"", image));
                }

                style_recycler.setAdapter(new StylesAdapter(styles));
                style_recycler.setLayoutManager(new GridLayoutManager(context,2));


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
