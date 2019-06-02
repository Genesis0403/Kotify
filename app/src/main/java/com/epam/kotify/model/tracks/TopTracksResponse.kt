package com.epam.kotify.model.tracks

import com.google.gson.annotations.SerializedName

data class TopTracksResponse(
    @SerializedName("tracks") val topTracks: TopTracks?
)