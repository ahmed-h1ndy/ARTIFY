package com.ahmed.artify.artsy.Artists

import com.google.gson.annotations.SerializedName

data class Links(@SerializedName("self") val self: Link,
                 @SerializedName("next") val next: Link
)
