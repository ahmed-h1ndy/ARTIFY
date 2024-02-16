package com.ahmed.artify.explore;

import com.google.gson.annotations.SerializedName;

public class Painting {
    @SerializedName("id")
    private  int P_id;
    @SerializedName("Artist_id")
    private  int P_Artist_id;
    @SerializedName("art_style_id")
    private  int P_art_style_id;
    @SerializedName("image")
    private  String P_image;

    public Painting(int id, int artist_id, int art_style_id, String image) {
        P_id = id;
        P_Artist_id = artist_id;
        P_art_style_id = art_style_id;
        P_image = image;
    }

    public int getP_id() {
        return P_id;
    }

    public int getP_Artist_id() {
        return P_Artist_id;
    }

    public int getP_art_style_id() {
        return P_art_style_id;
    }

    public String getP_image() {
        return P_image;
    }
}
