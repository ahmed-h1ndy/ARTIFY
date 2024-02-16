package com.ahmed.artify.explore;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArtistData {

    @SerializedName("data")
    private List<Artist> ArtistList;

    public List<Artist> getArtistList() {
        return ArtistList;
    }
}
