package com.epam.kotify.model.tracks

import com.epam.kotify.model.Image
import com.epam.kotify.model.artists.Artist
import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("duration") val duration: Long?,
    @SerializedName("listeners") val listeners: Int?,
    @SerializedName("artist") val artist: Artist?,
    @SerializedName("image") val image: List<Image>?,
    @SerializedName("name") val name: String?
)