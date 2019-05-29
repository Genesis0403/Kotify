package com.epam.kotify.model.artists

import com.epam.kotify.model.Image
import com.google.gson.annotations.SerializedName

data class Artist(
    @SerializedName("name") val name: String?,
    @SerializedName("image") val image: Image?,
    @SerializedName("playcount") val playCount: Int?
)