package com.ahmed.artify.artsy.Artists

import com.google.gson.annotations.SerializedName

data class ArtistLinks(@SerializedName("thumbnail") val thumbnail: Link,
                       @SerializedName("image") val image: ImageLink,
                       @SerializedName("self") val self: Link,
                       @SerializedName("permalink") val permalink: Link,
                       @SerializedName("artworks") val artworks: Link,
                       @SerializedName("published_artworks") val publishedArtworks: Link,
                       @SerializedName("similar_artists") val similarArtists: Link,
                       @SerializedName("similar_contemporary_artists") val similarContemporaryArtists: Link,
                       @SerializedName("genes") val genes: Link
)
