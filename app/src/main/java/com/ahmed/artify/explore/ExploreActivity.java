package com.ahmed.artify.explore;



import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmed.artify.R;



public class ExploreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);
        TextView textView=findViewById(R.id.textView8);
        ImageView image=findViewById(R.id.imageView);
/*
        OkHttpClient okHttpClient=new OkHttpClient.Builder().build();

        Request request=new Request.Builder().url("http://192.168.1.3:5000/get_paint/2").build();

       okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(ExploreActivity.this,"network not found",Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                        System.out.println("response problem");
                        Log.e("YourTag", "Request failed: " + e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if(response.isSuccessful()) {
                        // assert response.body() != null;
                        //String myresponse=response.body().string();
                        try (BufferedReader reader = new BufferedReader(response.body().charStream())) {
                            StringBuilder stringBuilder = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                stringBuilder.append(line);
                            }

                         String myresponse = stringBuilder.toString();

                        Gson gson = new Gson();
                        String json = myresponse;
                        Data data = gson.fromJson(json, Data.class);

                        List<Painting> paintingList = data.getDataList();
                        System.out.println(myresponse);

                        ExploreActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(ExploreActivity.this, "response recieved", Toast.LENGTH_LONG).show();

                                System.out.println(paintingList.get(0).getP_Artist_id());
                                // Type paintingType=new TypeToken<List<Painting>>(){}.getType();

                                textView.setText(String.valueOf(paintingList.get(0).getP_Artist_id()));

                            }
                        });
                      }
                        catch (IOException e)
                        {
                         e.printStackTrace();
                        }
                    }
                    else{
                        Log.i("listDataVal", "couldn't get the response'");
                    }



            }
        });



*/

/*
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.1.3:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface=retrofit.create(ApiInterface.class);

        Call<PaintingData> call=apiInterface.get_all_paints();

        call.enqueue(new Callback<PaintingData>() {
            @Override
            public void onResponse(Call<PaintingData> call, Response<PaintingData> response) {
                textView.setText(String.valueOf(response.body().getDataList().get(0).getP_id()));

            }

            @Override
            public void onFailure(Call<PaintingData> call, Throwable t) {
                textView.setText(t.getMessage());
                System.out.println(t.getMessage());
                System.out.println(t.getStackTrace().toString());
            }
        });
        */
    ApiRequests apiRequests=new ApiRequests();


       // List<Painting> paintingList = apiRequests.getAllPaintings(textView,image);
        //apiRequests.getPaintById(2);
        //apiRequests.getAllArtists();
        //apiRequests.getAllPaintingsByArtistId(2);
       // apiRequests.getAllArtStyles();
       // apiRequests.getAllPaintingsByStyleId(2);

/*
        byte[] Bytes= apiRequests.decodeImage("");
        Bitmap decodedImage = BitmapFactory.decodeByteArray(Bytes, 0, Bytes.length);

        // Display the Bitmap in an ImageView
        image.setImageBitmap(decodedImage);
*/




    }






}