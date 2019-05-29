package com.epam.kotify.model.tracks

import com.google.gson.annotations.SerializedName

data class TopTracks(
    @SerializedName("track") val topTracks: List<Track>
)