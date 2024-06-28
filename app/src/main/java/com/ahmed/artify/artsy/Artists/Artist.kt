package com.ahmed.artify.artsy.Artists

import com.google.gson.annotations.SerializedName

data class Artist(@SerializedName("id") val id: String,
                  @SerializedName("slug") val slug: String,
                  @SerializedName("created_at") val createdAt: String,
                  @SerializedName("updated_at") val updatedAt: String,
                  @SerializedName("name") val name: String,
                  @SerializedName("sortable_name") val sortableName: String,
                  @SerializedName("gender") val gender: String,
                  @SerializedName("biography") val biography: String,
                  @SerializedName("birthday") val birthday: String,
                  @SerializedName("deathday") val deathday: String?,
                  @SerializedName("hometown") val hometown: String,
                  @SerializedName("location") val location: String,
                  @SerializedName("nationality") val nationality: String,
                  @SerializedName("target_supply") val targetSupply: Boolean,
                  @SerializedName("image_versions") val imageVersions: List<String>,
                  @SerializedName("_links") val artistLinks: ArtistLinks
)
