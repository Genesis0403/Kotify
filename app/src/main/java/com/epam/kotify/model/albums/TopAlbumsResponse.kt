package com.epam.kotify.model.albums

import com.google.gson.annotations.SerializedName

data class TopAlbumsResponse(
    @SerializedName("topalbums") val topAlbums: TopAlbums
)