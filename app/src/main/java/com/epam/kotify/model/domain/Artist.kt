package com.epam.kotify.model.domain

/**
 * Model class which is used to represent API responses in [RecyclerView].
 *
 * @author Vlad Korotkevich
 */
data class Artist(
    val name: String,
    val image: String,
    val playCount: Int
)