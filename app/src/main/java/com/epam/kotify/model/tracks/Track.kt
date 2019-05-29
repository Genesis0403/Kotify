package com.epam.kotify.model.tracks

import com.epam.kotify.model.Artist
import com.epam.kotify.model.Image
import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("duration") val duration: Int,
    @SerializedName("playcount") val playCount: Int,
    @SerializedName("artist") val artist: Artist,
    @SerializedName("image") val image: Image,
    @SerializedName("name") val name: String
)