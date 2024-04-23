package com.ahmed.artify.RetrofitClass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaintingData {
    @SerializedName("data")
    private List<Painting> paintingList;

    public List<Painting> getDataList() {
        return paintingList;
    }
}