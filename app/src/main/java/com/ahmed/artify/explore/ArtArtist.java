package com.ahmed.artify.explore;

import com.google.gson.annotations.SerializedName;

public class ArtArtist {


    @SerializedName("id")
    private  int A_id;
    @SerializedName("Artist_name")
    private  String name;
    @SerializedName("image")
    private  String A_image;

    public int getA_id() {
        return A_id;
    }

    public String getName() {
        return name;
    }

    public String getA_image() {
        return A_image;
    }
}
