package com.epam.kotify.model.tracks

import com.google.gson.annotations.SerializedName

/**
 * Second level class which is used in API requests.
 *
 * @see TopTracksResponse

 * @author Vlad Korotkevich
 */
data class TopTracks(
    @SerializedName("track") val tracks: List<Track>?
)