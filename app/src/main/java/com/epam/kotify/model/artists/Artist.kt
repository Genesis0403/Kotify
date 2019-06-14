package com.epam.kotify.model.artists

import androidx.room.PrimaryKey
import com.epam.kotify.model.Image
import com.google.gson.annotations.SerializedName

/**
 * Entity class which is used as DB table and API response.
 *
 * @author Vlad Korotkevich
 */

data class Artist(

    @PrimaryKey
    @field:SerializedName("name") val name: String = "None",

    @field:SerializedName("image") val image: List<Image>? = null,

    @field:SerializedName("listeners") val listeners: Int? = null
)