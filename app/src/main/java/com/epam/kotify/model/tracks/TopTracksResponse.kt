package com.epam.kotify.model.tracks

import com.google.gson.annotations.SerializedName

data class TopTracksResponse(
    @SerializedName("toptracks") val topTracks: List<Track>
)