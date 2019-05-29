package com.epam.kotify.model.artists

import com.google.gson.annotations.SerializedName

data class TopArtistsResponse(
    @SerializedName("topartists") val topArtist: List<Artist>
)