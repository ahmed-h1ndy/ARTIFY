package com.ahmed.artify.RetrofitClass;



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

        ApiRequests apiRequests=new ApiRequests();

        apiRequests.getAllPaintings(textView, image);


    }






}