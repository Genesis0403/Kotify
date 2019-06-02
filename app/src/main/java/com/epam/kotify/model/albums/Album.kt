package com.epam.kotify.model.albums

import com.epam.kotify.model.Image
import com.epam.kotify.model.artists.Artist
import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("artists") val artist: Artist?,
    @SerializedName("image") val image: List<Image?>,
    @SerializedName("playcount") val playCount: Int?,
    @SerializedName("name") val title: String?
)