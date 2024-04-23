package com.ahmed.artify.RetrofitClass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArtStyleData {

    @SerializedName("data")
    private List<ArtStyle> ArtStyleList;

    public List<ArtStyle> getArtStyleList() {
        return ArtStyleList;
    }

}
