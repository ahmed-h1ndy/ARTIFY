package com.ahmed.artify.artsy.Artists

import com.google.gson.annotations.SerializedName

data class ImageLink(@SerializedName("href") val href: String,
                     @SerializedName("templated") val templated: Boolean)
