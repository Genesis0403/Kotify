package com.epam.kotify.model

import com.google.gson.annotations.SerializedName

/**
 * Class which represents API response of image urls.
 *
 * @author Vlad Korotkevich
 */
data class Image(
    @SerializedName("size") val size: String,
    @SerializedName("#text") val url: String
)