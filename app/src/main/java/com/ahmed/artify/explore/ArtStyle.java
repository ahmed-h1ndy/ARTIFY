package com.ahmed.artify.explore;

import com.google.gson.annotations.SerializedName;

public class ArtStyle {
    @SerializedName("id")
    private  int S_id;
    @SerializedName("Style_name")
    private  String S_name;
    @SerializedName("image")
    private  String S_image;

    public int getS_id() {
        return S_id;
    }

    public String getS_name() {
        return S_name;
    }

    public String getS_image() {
        return S_image;
    }
}
