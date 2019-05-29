package com.epam.kotify.model.albums

import com.epam.kotify.model.Artist
import com.epam.kotify.model.Image
import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("artist") val artist: Artist,
    @SerializedName("image") val image: List<Image>,
    @SerializedName("playcount") val playCount: Int,
    @SerializedName("name") val title: String
)