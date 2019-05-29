package com.epam.kotify.model.albums

import com.google.gson.annotations.SerializedName

data class TopAlbums(
    @SerializedName("album") val albums: List<Album>?
)