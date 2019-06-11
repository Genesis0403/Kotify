package com.epam.kotify.model.domain

/**
 * Model class which is used to represent API responses in [RecyclerView].
 *
 * @author Vlad Korotkevich
 */

data class Track(
    val title: String,
    val artist: String,
    val image: String,
    val duration: Long,
    val listeners: Int
)