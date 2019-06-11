package com.epam.kotify.model.tracks

import com.google.gson.annotations.SerializedName

/**
 * First level class of API response.
 *
 * @author Vlad Korotkevich
 */

data class TopTracksResponse(
    @SerializedName("tracks") val topTracks: TopTracks?
)