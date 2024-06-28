package com.ahmed.artify.artsy.Artists

import com.google.gson.annotations.SerializedName

data class Embedded(@SerializedName("artists") val artists: List<Artist>)
