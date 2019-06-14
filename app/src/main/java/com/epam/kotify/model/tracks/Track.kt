package com.epam.kotify.model.tracks

import androidx.room.PrimaryKey
import com.epam.kotify.model.Image
import com.epam.kotify.model.artists.Artist
import com.google.gson.annotations.SerializedName

/**
 * Entity class which is used as DB table and API response.
 *
 * @author Vlad Korotkevich
 */

data class Track(
    @PrimaryKey
    @field:SerializedName("name") val name: String = "None",
    @field:SerializedName("duration") val duration: Long? = 0,
    @field:SerializedName("listeners") val listeners: Int? = 0,
    @field:SerializedName("artist") val artist: Artist? = null,
    @field:SerializedName("image") val image: List<Image>? = null
)