package com.epam.kotify.model.artists

import com.google.gson.annotations.SerializedName

/**
 * First level class of API response.
 *
 * @author Vlad Korotkevich
 */

data class TopArtistsResponse(
    @SerializedName("topartists") val topArtists: TopArtists?
)