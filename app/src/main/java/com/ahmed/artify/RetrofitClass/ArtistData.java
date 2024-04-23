package com.ahmed.artify.RetrofitClass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArtistData {

    @SerializedName("data")
    private List<ArtArtist> artArtistList;

    public List<ArtArtist> getArtistList() {
        return artArtistList;
    }
}
