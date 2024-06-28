package com.ahmed.artify.RetrofitClass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmed.artify.Helpers.ArtistLocal;
import com.ahmed.artify.Helpers.Style;

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
    public final ApiInterface apiInterface;
    Call<PaintingData> callPainting;
    Call<ArtistData> callArtist;
    Call<ArtStyleData>callArtStyle;
    public ApiRequests() {
         retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.1.102:5000")
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
    private List<ArtArtist> temp_artists;
    public ArrayList<ArtistLocal> initialize_artists(onArtistsInitializedListener listener)
    {
        callArtist =apiInterface.get_all_artists();
        ArrayList<ArtistLocal> artistLocals = new ArrayList<>();
        callArtist.enqueue(new Callback<ArtistData>() {
            @Override
            public void onResponse(Call<ArtistData> call, Response<ArtistData> response) {

                temp_artists =response.body().getArtistList();
                String name;
                Bitmap image;
                for(int i = 0; i< temp_artists.size(); i++){
                    name = String.valueOf(temp_artists.get(i).getName());
                    byte[] Bytes= decodeImage(temp_artists.get(i).getA_image());
                    image = BitmapFactory.decodeByteArray(Bytes, 0, Bytes.length);
                    artistLocals.add(new ArtistLocal(name, image));
                }
                if(listener!=null){
                    listener.onArtistsInitialized(artistLocals);
                }
            }
            @Override
            public void onFailure(Call<ArtistData> call, Throwable t) {
                Log.e("use corutoines baby", t.getMessage());
                //initialize_artists(listener);
            }
        });
        try {
            callArtist.wait();
        }
        catch(Exception e){
            Log.i("use corutoins baby", e.getMessage());
        }
        return artistLocals;
    }

    public interface onArtistsInitializedListener{
        void onArtistsInitialized(ArrayList<ArtistLocal> artistLocals);
    }
    private List<ArtStyle> ArtStyleArrayList;
    public ArrayList<Style> initialize_styles(final OnStylesInitializedListener listener)
    {
        callArtStyle =apiInterface.get_all_styles();
        ArrayList<Style> styles = new ArrayList<>();
        callArtStyle.enqueue(new Callback<ArtStyleData>() {
            @Override
            public void onResponse(Call<ArtStyleData> call, Response<ArtStyleData> response) {

                ArtStyleArrayList=response.body().getArtStyleList();
                String name;
                Bitmap image;
                for(int i = 0;i<ArtStyleArrayList.size();i++){
                    name = String.valueOf(ArtStyleArrayList.get(i).getS_name());
                    byte[] Bytes= decodeImage(ArtStyleArrayList.get(i).getS_image());
                    image = BitmapFactory.decodeByteArray(Bytes, 0, Bytes.length);
                    styles.add(new Style(name,"", image));
                }
                if (listener != null) {
                    listener.onStylesInitialized(styles);
                }
            }

            @Override
            public void onFailure(Call<ArtStyleData> call, Throwable t) {
                Log.e("use corutoines baby", t.getMessage());
                //initialize_styles(listener);

            }
        });
        return styles;
    }

    public interface OnStylesInitializedListener {
        void onStylesInitialized(ArrayList<Style> styles);
    }

    /*
    private void initializeStyles(final RecyclerView styleRecyclerView, final Context context, final OnStylesInitializedListener listener) {
    callArtStyle = apiInterface.get_all_styles();
    callArtStyle.enqueue(new Callback<ArtStyleData>() {
        @Override
        public void onResponse(Call<ArtStyleData> call, Response<ArtStyleData> response) {
            ArtStyleArrayList = response.body().getArtStyleList();
            // Assuming ArtStyleArrayList is a member variable
            List<Style> styles = new ArrayList<>();

            String name;
            Bitmap image;
            for (int i = 0; i < ArtStyleArrayList.size(); i++) {
                name = String.valueOf(ArtStyleArrayList.get(i).getS_name());
                byte[] Bytes = decodeImage(ArtStyleArrayList.get(i).getS_image());
                image = BitmapFactory.decodeByteArray(Bytes, 0, Bytes.length);
                styles.add(new Style(name, "", image));
            }

            styleRecyclerView.setAdapter(new StylesAdapter(styles));
            styleRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));

            if (listener != null) {
                listener.onStylesInitialized(styles);
            }
        }

        @Override
        public void onFailure(Call<ArtStyleData> call, Throwable t) {
            // Handle failure if needed
        }
    });
}

// Define an interface for the callback
interface OnStylesInitializedListener {
    void onStylesInitialized(List<Style> styles);
} */
    public  byte[] decodeImage(String base64Img)
    {
        byte[] imageByte;
        return Base64.getDecoder().decode(base64Img);
    }
}
