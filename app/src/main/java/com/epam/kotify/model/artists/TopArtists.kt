package com.epam.kotify.model.artists

import com.google.gson.annotations.SerializedName

data class TopArtists(
    @SerializedName("artist") val artists: List<Artist>?
)