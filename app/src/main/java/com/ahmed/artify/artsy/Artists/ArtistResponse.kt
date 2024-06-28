package com.ahmed.artify.artsy.Artists

import com.google.gson.annotations.SerializedName

data class ArtistResponse(@SerializedName("total_count") val totalCount: Any?,
                          @SerializedName("_links") val links: Links,
                          @SerializedName("_embedded") val embedded: Embedded
)
