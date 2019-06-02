package com.epam.kotify.model.domain

data class Track(
    val title: String,
    val artist: String,
    val image: String,
    val duration: Long,
    val listeners: Int
)