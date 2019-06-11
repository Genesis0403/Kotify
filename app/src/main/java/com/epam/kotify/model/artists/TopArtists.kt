package com.epam.kotify.model.artists

import com.google.gson.annotations.SerializedName

/**
 * Second level class which is used in API requests.
 *
 * @see TopArtistsResponse

 * @author Vlad Korotkevich
 */
data class TopArtists(
    @SerializedName("artist") val artists: List<Artist>?
)